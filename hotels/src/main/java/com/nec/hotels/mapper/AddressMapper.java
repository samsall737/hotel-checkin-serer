package com.nec.hotels.mapper;


import com.nec.hotels.entity.Address;

public class AddressMapper {
	
	  
    public static Address createAddress(com.nec.pms.model.guest.Address address) {
    	Address guestAddress = new Address();
		int size = 0;
		if (address != null && address.getAddress() != null) {
			size = address.getAddress().size();
		}

		if (size > 0) {
			guestAddress.setStreetAddressFirst(address.getAddress().get(0));
		}
		if (size > 1) {
			guestAddress
					.setStreetAddressSecond(address.getAddress().get(1));
		}
		if (size > 2) {
			guestAddress.setStreetAddressThird(address.getAddress().get(2));
		}
		guestAddress.setCity(address.getCity());
		guestAddress.setCountry(address.getCountryCode());
		guestAddress.setPostalCode(address.getPostalCode());
		guestAddress.setState(address.getState());
		return guestAddress;
    }

}
