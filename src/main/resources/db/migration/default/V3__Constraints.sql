alter table driver_assignment_status add constraint check_ride_status check (ride_status in ('ON_THE_WAY','ASSIGNED','COMPLETE'));
