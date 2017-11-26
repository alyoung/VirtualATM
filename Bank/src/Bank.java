import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Scanner;


public class Bank implements Serializable{
	private Scanner input;
	private Customer[] customers;
	private String userID;
	private Customer user;
	private AccountLog accountLog;
	private FileWriter fw;
	private ObjectOutputStream writeInfo;
	
	Bank() throws IOException, ClassNotFoundException{
		input = new Scanner(System.in);
		customers = new Customer[500];   //500���� ���� �ִ�.
		for(int i = 0; i < 500; i++){
			customers[i] = new Customer();
		}
		userID = new String();
		user = new Customer();
		loadCustomerInfo();
		accountLog = new AccountLog();
		//FileWriter fw = new FileWriter("CustomerInfo.data");    //���� ���� �����
		writeInfo = new ObjectOutputStream (new FileOutputStream ("CustomerInfo.data"));
	}


	
	private void loadCustomerInfo() throws IOException, ClassNotFoundException{
		try {
			ObjectInputStream readInfo = new ObjectInputStream(new FileInputStream("CustomerInfo.data"));
			for(int i = 0; i < 500; i++){
				customers[i] = (Customer) readInfo.readObject();
			}
			readInfo.close();
		} catch (FileNotFoundException e) {
				System.out.println("�� ������ ������ �����ϴ�.");
		} catch (Exception eOFException) {
			
		}
	}
	
	public Boolean certifyCustomer() throws ClassNotFoundException, IOException{
		loadCustomerInfo();
		System.out.print("���̵� �Է��ϼ��� : ");
		String customerID = input.nextLine();
		System.out.print("��й�ȣ�� �Է��ϼ��� : ");
		String password = input.nextLine();
		try {
			for(int i = 0; i < 500; i++){
				if(customers[i].id.equals(customerID)){
					if(customers[i].password.equals(password)){
						userID = customerID;
						user = customers[i];
						System.out.println("�� ������ �����Ͽ����ϴ�.");
						return true;
					}
					break;
				}
			}
			System.out.println("�� ������ �����Ͽ����ϴ�.");
			return false;
		} catch (Exception nullpointerException) {
			return false;
		}
	}
	
	public void searchBalance() throws ClassNotFoundException, IOException{
		loadCustomerInfo();
		System.out.println("�ܰ�� " + user.remain +" �Դϴ�.");
	}
	
	public void deposit() throws ClassNotFoundException, IOException{
		loadCustomerInfo();
		System.out.print("�Ա� �� �ݾ��� �Է� : ");
		int deposit = input.nextInt();
		user.remain += deposit;
		System.out.println("�Ա��� �Ϸ��Ͽ����ϴ�.");
		
		AccountLog newAccountLog = new AccountLog();
		newAccountLog = getTime();
		newAccountLog.type = "�Ա�";
		newAccountLog.amount = deposit;
		ObjectOutputStream writeInfo;
		File file = new File("AccountLog.txt");
		try {
			FileWriter write = new FileWriter(file,true);
			write.write(Integer.toString(newAccountLog.year) + "/" + Integer.toString(newAccountLog.month) + "/" + Integer.toString(newAccountLog.day) +
			"/" + newAccountLog.type + "/" + Integer.toString(newAccountLog.amount) + "\n");
			write.flush();
			write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("�Ա� ������ ����� �� �����ϴ�.");
		}
	}
	
	public void withdraw(){
		System.out.print("��� �� �ݾ��� �Է� : ");
		int withdraw = input.nextInt();
		if(user.remain - withdraw > 0){
			user.remain -= withdraw;
			System.out.println("����� �Ϸ��Ͽ����ϴ�.");
			
			AccountLog newAccountLog = new AccountLog();
			newAccountLog = getTime();
			newAccountLog.type = "���";
			newAccountLog.amount = withdraw;
			ObjectOutputStream writeInfo;
			File file = new File("AccountLog.txt");
			try {
				FileWriter write = new FileWriter(file,true);
				write.write(Integer.toString(newAccountLog.year) + "/" + Integer.toString(newAccountLog.month) + "/" + Integer.toString(newAccountLog.day) +
				"/" + newAccountLog.type + "/" + Integer.toString(newAccountLog.amount) + "\n");
				write.flush();
				write.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("��� ������ ����� �� �����ϴ�.");
			}
			
		}
		else{
			System.out.println("�ܾ��� �����մϴ�.");
		}
	}
	
	public void creditInformation(){
		String readLine = new String();
		File file = new File("AccountLog.txt");
		try {
			Scanner read = new Scanner(file);
			do{
				readLine = read.nextLine();
				System.out.println(readLine);
			}while(read.hasNext());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void showAccountInfo(){
		System.out.println("������ �̸��� " + user.name + " �Դϴ�.");
		System.out.println("������ �ֹι�ȣ�� " + user.residentNumber + " �Դϴ�.");
		System.out.println("������ ��ȭ��ȣ�� " + user.telephone + " �Դϴ�.");
		System.out.println("������ �ּҴ� " + user.address + " �Դϴ�.");
		System.out.println("������ ���̵�� " + user.id + " �Դϴ�.");
		System.out.println("������ ��й�ȣ�� " + user.password + " �Դϴ�.");
	}
	
	public void registerCustomer() throws ClassNotFoundException, IOException{  
		Customer newCustomer = new Customer();
		System.out.print("�̸��� �Է��ϼ��� : ");
		newCustomer.name = input.nextLine();
		System.out.print("�ֹε�Ϲ�ȣ�� �Է��ϼ��� : ");
		newCustomer.residentNumber = input.nextLine();
		System.out.print("��ȭ��ȣ�� �Է��ϼ��� : ");
		newCustomer.telephone = input.nextLine();
		System.out.print("�ּҸ� �Է��ϼ��� : ");
		newCustomer.address = input.nextLine();
		System.out.print("���̵� �Է��ϼ��� : ");
		newCustomer.id = input.nextLine();
		for(int i = 0; i < 500; i++){
			if(newCustomer.id.equals(customers[i].id)){
				System.out.println("�ߺ��� id���Դϴ�.");
				return;
			}
		}
		System.out.print("�н����带 �Է��ϼ��� : ");
		newCustomer.password = input.nextLine();
		
		
		try {
			writeInfo.writeObject(newCustomer);
			writeInfo.flush();
			//writeInfo.close();
		} catch (FileNotFoundException e) {
			System.out.println("�� ������ ������ �����ϴ�.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadCustomerInfo();
	}
	
	public void exit(){
		System.out.println("���� ���α׷��� �����մϴ�.");
		System.exit(0);
	}
	
	private AccountLog getTime(){
		Calendar cal = Calendar.getInstance();
		AccountLog time = new AccountLog();
		time.year = cal.get(Calendar.YEAR);
		time.month = cal.get(Calendar.MONTH);
		time.day = cal.get(Calendar.DAY_OF_MONTH);
		return time;
	}

}
