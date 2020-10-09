CREATE OR REPLACE FUNCTION distance(lat1 decimal, lon1 decimal, lat2 decimal, lon2 decimal)
RETURNS decimal AS $$
    DECLARE
        dist decimal = 0;
        radlat1 decimal;
        radlat2 decimal;
        theta decimal;
        radtheta decimal;
    BEGIN
        IF lat1 = lat2 OR lon1 = lon2
            THEN RETURN dist;
        ELSE
            radlat1 = pi() * lat1 / 180;
            radlat2 = pi() * lat2 / 180;
            theta = lon1 - lon2;
            radtheta = pi() * theta / 180;
            dist = sin(radlat1) * sin(radlat2) + cos(radlat1) * cos(radlat2) * cos(radtheta);
            IF dist > 1 THEN dist = 1; END IF;
            dist = acos(dist);
            dist = dist * 180 / pi();
            dist = dist * 60 * 1.1515;
            RETURN dist*1.609344;
        END IF;
    END;
   $$ LANGUAGE plpgsql