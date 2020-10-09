package com.loginext.taxidriver.service;

import com.loginext.taxidriver.entity.Driver;
import com.loginext.taxidriver.enums.FreeStatus;
import com.loginext.taxidriver.exception.BadRequestException;
import com.loginext.taxidriver.helper.Constants;
import com.loginext.taxidriver.helper.ErrorMessages;
import com.loginext.taxidriver.helper.Utils;
import com.loginext.taxidriver.models.DriverRegistration;
import com.loginext.taxidriver.models.DriverStatus;
import com.loginext.taxidriver.models.PaginatedTable;
import com.loginext.taxidriver.ras.DriverResourceAccessor;
import com.loginext.taxidriver.repository.IDriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    private IDriverRepo iDriverRepo;

    @Autowired
    private DriverResourceAccessor driverResourceAccessor;

    /**
     * registers driver along with its current location
     * @param driverRegistration
     * @return
     */
    @Transactional
    public Driver registerDriver(DriverRegistration driverRegistration){

        Optional<Driver> driverOptional = iDriverRepo.findByDriverName(driverRegistration.getDriverName());

        if(driverOptional.isPresent()){
            throw new BadRequestException(String.format(Constants.DRIVER_EXISTS, driverRegistration.getDriverName()));
        }

        if(driverRegistration.getCurrentLatitude()==null || driverRegistration.getCurrentLongitude()==null||
                !Utils.validateCoordinateRequest(driverRegistration)){
            throw new BadRequestException(ErrorMessages.LAT_LONG_INCORRECT);
        }

        Driver driver = new Driver();

        driver.setFreeStatus(true);
        driver.setDriverName(driverRegistration.getDriverName());
        driver.setLatitude(driverRegistration.getCurrentLatitude());
        driver.setLongitude(driverRegistration.getCurrentLongitude());

        return iDriverRepo.save(driver);

    }

    /**
     * Get all driver's statuses. Response is paginated.
     * With default page as 1 and page size as 50
     * @param allRequestParams
     * @return
     */
    public PaginatedTable getDriversStatus(Map<String,String> allRequestParams){

        String pageNumberStr = allRequestParams.get("page");
        String pageSizeStr = allRequestParams.get("pageSize");

        int page = 1;
        int pageSize = 50;

        try{
            page = StringUtils.isEmpty(pageNumberStr) ? page: Integer.parseInt(pageNumberStr.trim());
            pageSize = StringUtils.isEmpty(pageSizeStr) ? pageSize: Integer.parseInt(pageSizeStr.trim());

            if(page<=0){
                page = 1;
            }
            if(pageSize<=0){
                pageSize = 50;
            }

        }catch (NumberFormatException e){
            page = 1;
            pageSize = 50;
        }

        List<Object[]> result = driverResourceAccessor.getAllDriverStatus(page,pageSize);

        PaginatedTable paginatedTable = new PaginatedTable();
        paginatedTable.setPageNumber(page);
        paginatedTable.setPageSize(pageSize);

        if(!result.isEmpty()){
            paginatedTable.setTotalCount(new BigInteger(result.get(0)[3].toString()));
        }

        paginatedTable.setData(createDriverStatusResponse(result));

        return paginatedTable;

    }

    private List<DriverStatus> createDriverStatusResponse(List<Object[]> result) {

        List<DriverStatus> driverStatuses = new ArrayList<>();


        for(Object[] row: result){
            DriverStatus driverStatus = new DriverStatus();
            driverStatus.setDriverName(row[0].toString());
            driverStatus.setUserName(row[1]==null? "":row[1].toString());
            driverStatus.setStatus((boolean)row[2] ? FreeStatus.AVAILABLE: FreeStatus.BUSY);
            driverStatuses.add(driverStatus);
        }

        return driverStatuses;

    }


}
