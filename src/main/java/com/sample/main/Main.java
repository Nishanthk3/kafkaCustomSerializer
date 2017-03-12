package com.sample.main;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import com.sample.consumer.KafkaConsumerServiceImpl;
import com.sample.pojo.Company;
import com.sample.pojo.DeciderEnum;
import com.sample.pojo.Employee;
import com.sample.pojo.Message;
import com.sample.producer.KafkaProducerServiceImpl;

public class Main 
{
    public static void main( String[] args ) throws InterruptedException, ExecutionException
    {
        KafkaProducerServiceImpl producer = new KafkaProducerServiceImpl();
        KafkaConsumerServiceImpl consumer = new KafkaConsumerServiceImpl();
        
        //consumer.consume((Message i) -> System.out.println("id : "+i.getId() + ", message :"+ i.getMessage() + ", date :" +i.getDate()));

		consumer.consume((Message i) -> System.out.println("id : "+i.getId() + ", message :"+ i.getMessage() + ", date :" +i.getDate() + ", entity :" +i.getEntity()), 
				(Message i) -> {
			if (i.getClazz() == Employee.class) {
				System.out.println("Employee Object");
				return DeciderEnum.SUCCESS;
			} else {
				System.out.println("Company Object");
				return DeciderEnum.FAIL;
			}
		});
		Message message = null;

		for (int i = 0;; i++) {
			message = new Message();
			message.setId(i);
			message.setMessage("This is messsage : " + i);
			message.setDate(new Date());
			if(i%2 == 0) {
				Employee emp = getEmployee(i);
				message.setEntity(emp);
				message.setClazz(emp.getClass());
				producer.sendMessage(true, Employee.class.getSimpleName(), message);
			}
			else {
				Company comp = getCompany(i);
				message.setEntity(comp);
				message.setClazz(comp.getClass());
				producer.sendMessage(true, Company.class.getSimpleName(), message);
			}
			Thread.sleep(2000);
		}
	}

	private static Employee getEmployee(int i) {
		Employee emp = new Employee();
		emp.setAge(i);
		emp.setName("EmployeeName_"+i);
		emp.setCompany("EmployeeCompany_"+i);
		return emp;
	}
	
	private static Company getCompany(int i) {
		Company comp = new Company();
		comp.setYear(i);
		comp.setName("CompanyName_"+i);
		comp.setAddress("CompanyAddress_"+i);
		return comp;
	}
}