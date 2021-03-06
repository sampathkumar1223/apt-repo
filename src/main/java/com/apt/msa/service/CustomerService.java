package com.apt.msa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apt.msa.entity.Customer;
import com.apt.msa.exception.APTException;
import com.apt.msa.jpa.repository.CustomerRespository;
import com.apt.msa.util.CryptoUtil;

@Service
public class CustomerService implements ICustomerService {

	//@Autowired
	//private CustomerCRUDRepository customerCRUDRepository;

	
	@Autowired
	private CustomerRespository customerRepository;
	
	/**
	 * create customer to DB
	 */
	public Customer createCustomer(Customer customer) throws APTException {

		return customerRepository.saveAndFlush(customer);

	}
	
	/**
	 * customer login 
	 */
	public Customer login(final String userName, final String password) throws APTException {

		System.out.println("::::::::::::: login username:"+userName+"  password:"+password);
		
		List<Customer>  customerList = customerRepository.fetchByUserName(userName);
		
		if(null!= customerList && customerList.size() > 0 ){
			//Customer customer = customerList.iterator().next();
			final Customer customer = customerList.get(0);
			try {
				
				final String decryptedPwd = CryptoUtil.decrypt(customer.getPassword()).toString();
				System.out.println("password::::"+decryptedPwd +" password:"+password);
				System.out.println(decryptedPwd.equals(password));
				if(userName.equals(customer.getUserName()) && (decryptedPwd.equals(password)) ) {
					return customer;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return null;
	}

	@Override
	public Customer findOne(Long customerId) throws APTException {
		Customer customer = customerRepository.findOne(customerId);
		return customer;
	}

}
