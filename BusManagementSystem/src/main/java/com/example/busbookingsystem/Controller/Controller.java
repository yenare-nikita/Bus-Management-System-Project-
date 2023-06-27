package com.example.busbookingsystem.Controller;

import com.example.busbookingsystem.CustException.CustException;
import com.example.busbookingsystem.Models.*;
import com.example.busbookingsystem.Service.ServiceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private ServiceA service;

    @RequestMapping(value = "/addAdmin",method = RequestMethod.POST)
    public String addAdmin(@RequestBody AdminInfo adminInfo) throws CustException {
        System.out.println(adminInfo);
        service.addAdmin(adminInfo);
        return "Admin Added Successfully.....";
    }

    @PostMapping( "/addBus")
    public String addBus(@RequestBody BusAndAdmin busAndAdmin) throws CustException {
       // System.out.println(busAndAdmin.getBusInfo());
        //System.out.println(busAndAdmin.getAdminInfo());
        service.addBus(busAndAdmin.getBusInfo(),busAndAdmin.getAdminInfo());

        return "Bus Added Successfully.....";
    }

    @DeleteMapping ( "/deleteBus")
    public String deleteBus(@RequestBody BusAndAdmin busAndAdmin) throws CustException {
        service.deleteBus(busAndAdmin.getBusInfo().getBusno(),busAndAdmin.getAdminInfo());
        return "Bus deleted Successfully.....";
    }


    @PutMapping("/updatebus")
    public String updateBus(@RequestBody BusAndAdmin busAndAdmin) throws CustException {
        service.updateBus(busAndAdmin.getBusInfo(),busAndAdmin.getAdminInfo());
        return "Bus Updated Successfully";
    }

    @PostMapping( "/adduser")
    public String adduser(@RequestBody UserInfo userInfo) throws CustException {
        service.adduser(userInfo);
        return "User Added successfully";
    }


    @GetMapping( "/checkbus")
    public List<BusInfo> checkbus(@RequestBody BusInfo busInfo) throws CustException {
        List<BusInfo>buses=service.findbuses(busInfo.getSource(),busInfo.getDestination());
        if(buses.isEmpty())
        {
            throw new CustException("No buses found");
        }
        return buses;
    }

    @GetMapping( "/seatsavailaible")
    public String seatsavailable(@RequestBody BusInfo busInfo) throws CustException {
        Integer no=0;
        no=service.seats(busInfo.getBusno());
        return "No of available seats are "+no;

    }

    @PostMapping( "/bookseat")
    public String bookseat(@RequestBody BookingInfo bookingInfo) throws CustException {
        service.bookseats(bookingInfo);
        return "Seat Booked Successfully";
    }

    @DeleteMapping("/cancelseat")
    public String cancelseat(@RequestBody BookingInfo bookingInfo) throws CustException {
        service.cancel(bookingInfo);
        return "Seat canceled successfully";
    }
}
