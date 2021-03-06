package delectable.test.logic;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import delectable.dto.IdDTO;
import delectable.dto.MenuItemDTO;
import delectable.dto.OrderDTO;
import delectable.dto.OrderDetailDTO;
import delectable.dto.OrderDetailMenuDTO;
import delectable.dto.PersonalInfoDTO;
import delectable.logic.MenuManager;
import delectable.logic.OrderManager;

public class OrderManagerTest2 {

	@Test
	public void testAddOrder() throws Exception {
		//adding sample menu item
		MenuItemDTO tempMenuItem = new MenuItemDTO();
		tempMenuItem.setMinimum_order(2);
		tempMenuItem.setPrice_per_person(2.5f);
		MenuManager.getMenu().AddItem(tempMenuItem);

		OrderDTO ordToADD = new OrderDTO();
		ordToADD.setDelivery_address("sample address");
		ordToADD.setDelivery_date("20160424");
		//sample personal info
		PersonalInfoDTO pi = new PersonalInfoDTO(); 
		pi.setEmail("random@testemail.com");
		ordToADD.setPersonal_info(pi);

		List<OrderDetailMenuDTO> tempOrderList = new ArrayList<OrderDetailMenuDTO>();
		OrderDetailMenuDTO item = new OrderDetailMenuDTO();
		item.setId(0);
		item.setCount(2);
		tempOrderList.add(item);
		ordToADD.setOrder_detail(tempOrderList);

		OrderManager.getOrderMan().addOrder(ordToADD);

		//retrieving and checking
		OrderDetailDTO ord = new OrderDetailDTO();
		ord = OrderManager.getOrderMan().getOrder(0);
		boolean compare = false;
		if(ord.getAmount() == 5.0f)
			compare = true;
		assertEquals(compare, true);
		IdDTO oid = new IdDTO();
		oid.setId(ord.getId());
		OrderManager.getOrderMan().CancelOrder(oid);
		assertEquals(OrderManager.getOrderMan().getOrder(0).getStatus(),"cancelled");
		try{
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			Date dateobj = new Date();
			ordToADD.setDelivery_date(df.format(dateobj));
			OrderManager.getOrderMan().addOrder(ordToADD);
			IdDTO idD = new IdDTO();
			idD.setId(1);
			OrderManager.getOrderMan().CancelOrder(idD);
			
		}catch(Exception e){
			assertEquals(e.getLocalizedMessage()
					.equals("Cannot cancel, it's due today"),true);
		}
		
		try{
			tempOrderList = new ArrayList<OrderDetailMenuDTO>();
			item = new OrderDetailMenuDTO();
			item.setId(0);
			item.setCount(1);
			tempOrderList.add(item);
			ordToADD.setOrder_detail(tempOrderList);
			OrderManager.getOrderMan().addOrder(ordToADD);
			
		}catch(NumberFormatException n){
			assertEquals(n.getLocalizedMessage()
					.equals("Min order is not satisfied"),true);
			
		}
	}


}
