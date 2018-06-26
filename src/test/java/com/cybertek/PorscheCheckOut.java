package com.cybertek;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PorscheCheckOut {
	WebDriver driver;
	
	
	@BeforeClass /// runs once for all test
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		//1)Open browser
		driver = new ChromeDriver();
		//2)Go to url “https://www.porsche.com/usa/modelstart/”
		driver.get("https://www.porsche.com/usa/modelstart/");
		driver.manage().window().fullscreen();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}
	
	@Test
	public void setup() throws InterruptedException {
		
		//3)Select model 718
		driver.findElement(By.xpath("//img[@alt='Porsche - 718']")).click();
		//4)Remember the price of 718Cayman
		double basePrice = Double.parseDouble(driver.findElement(By.xpath("//div[@class='m-14-model-price'][contains(text(),'From $ 56,900.00*')]")).getText().replaceAll("[ From$,*]",""));
		

		
		//Click on Build & Price under 718Cayman
	    driver.findElement(By.xpath("(//span[.='Build & Price'])[1]")).click();
					
		//5)Perform the click operation that opens new window	
		Thread.sleep(3000);
	    //Switch to new window opened
		for(String winHandle : driver.getWindowHandles()){
		driver.switchTo().window(winHandle);}
		Thread.sleep(3000);

     	double actual = Double.parseDouble(driver.findElement(By.xpath("//section[@id='s_price']//div[@class='ccaPrice'][contains(text(),'$56,900')]")).getText().replaceAll("[,$]","")); //<div class="ccaPrice">$0</div>

		


       //6)Verify that Base price displayed on the page is same as the price from step 4
		Assert.assertEquals (actual, basePrice);
		System.out.println("base: " + basePrice);
		
		double ePrice = Double.parseDouble(driver.findElement(By.xpath("//section[@id='s_price']//div[@class='ccaPrice'][contains(text(),'$0')]")).getText().replaceAll("[$]", ""));

         //7)Verify that Price for Equipment is 0
		Assert.assertEquals(ePrice, (double)0);
		System.out.println("ePrice : " + ePrice);

		
		double deliveryPrice = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='ccaPrice'][contains(text(),'$1,050')])[2]")).getText().replaceAll("[$,]", ""));
		System.out.println("deliveryPrice" + deliveryPrice);

		
     	//8)Verify that total price is the sum of base price + Delivery, Processing and Handling Fee
		double totalPrice = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='ccaPrice'][contains(text(),'$57,950')])[2]")).getText().replaceAll("[$,]", ""));
         System.out.println("totalPrice : " + totalPrice);
		
		Assert.assertEquals(totalPrice, basePrice + deliveryPrice + ePrice);
		//9)Select color “Miami Blue”
		driver.findElement(By.xpath("//span[@style='background-color: rgb(0, 120, 138);']")).click();
		
		ePrice = Double.parseDouble(driver.findElement(By.xpath("//section[@id='s_price']//div[@class='ccaPrice'][contains(text(),'$2,580')]")).getText().replaceAll("[$,]",""));
		
		double miamiPrice = Double.parseDouble(driver.findElement(By.xpath("//div[@class='tt_price tt_cell'][contains(text(),'$2,580')]")).getText().replaceAll("[$,]", ""));
        //10)Verify that Price for Equipment is Equal to Miami Blue price
		Assert.assertEquals(miamiPrice, ePrice);
		
		//11)Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
		totalPrice = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='ccaPrice'][contains(text(),'$60,530')])[2]")).getText().replaceAll("[$,]", ""));

		
		Assert.assertEquals(totalPrice, basePrice + deliveryPrice + ePrice);
		
		//12)Select 20" Carrera Sport Wheels
		driver.findElement(By.xpath("(//span[@class='img-element'])[13]")).click();
		double wheelPrice = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='tt_price tt_cell'])[2]")).getText().replaceAll("[$,]", ""));
		
		ePrice = Double.parseDouble(driver.findElement(By.xpath("//section[@id='s_price']//div[@class='ccaPrice'][contains(text(),'$6,330')]")).getText().replaceAll("[$,]", ""));
		//13)Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels
		Assert.assertEquals(ePrice , miamiPrice + wheelPrice);
		
		totalPrice = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='ccaPrice'][contains(text(),'$64,280')])[2]")).getText().replaceAll("[$,]", ""));

		//14)Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
		Assert.assertEquals(totalPrice , basePrice + deliveryPrice + ePrice);
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,1000)", "");
		
		//15)Select seats ‘Power Sport Seats (14-way) with Memory Package’
		driver.findElement(By.id("s_interieur_x_PP06")).click();
		
		//16)Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package
		ePrice = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='ccaPrice'][contains(text(),'$8,660')])[2]")).getText().replaceAll("[$,]", ""));
		
		double seatPrice = Double.parseDouble(driver.findElement(By.xpath("(//div[.='$2,330'])[2]")).getText().replaceAll("[$, ]", ""));
		
		Assert.assertEquals(ePrice, miamiPrice + wheelPrice + seatPrice  );
		
		//17) Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
		
		totalPrice = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='ccaPrice'][contains(text(),'$66,610')])[2]")).getText().replaceAll("[$,]", ""));
		
		Assert.assertEquals(totalPrice, basePrice + deliveryPrice + ePrice );
		
		//18.Click on Interior Carbon Fiber
		JavascriptExecutor jse2 = (JavascriptExecutor) driver;
        jse2.executeScript("window.scrollBy(0,1000)", "");
        
        driver.findElement(By.xpath("//div[@id='IIC_subHdl']")).click();
        
        driver.findElement(By.xpath("//span[@id='vs_table_IIC_x_PEKH_x_c01_PEKH']")).click();
        
        //20.Verify that Price for Equipment is the sum of 
        //Miami Blue price + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package + Interior Trim in Carbon Fiber i.c.w. Standard Interior
        
        double intTrimPrice = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='pBox']//div[.='$1,540'])[2]")).getText().replaceAll("[$,]", ""));
        ePrice = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='ccaPrice'][contains(text(),'$10,200')])[2]")).getText().replaceAll("[$,]", ""));
        
        Assert.assertEquals(ePrice, miamiPrice + wheelPrice + seatPrice + intTrimPrice );
        
        //21.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
        
        totalPrice = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='ccaPrice'][contains(text(),'$68,150')])[2]")).getText().replaceAll("[$,]", ""));
        
        Assert.assertEquals(totalPrice, basePrice + deliveryPrice + ePrice);
        
        //22.Click on Performance
        
       driver.findElement(By.xpath("//div[@id='IMG_subHdl']")).click();
       
       //23.Select 7-speed Porsche Doppelkupplung (PDK)
       
       driver.findElement(By.xpath("//span[@id='vs_table_IMG_x_M250_x_c11_M250']")).click();
       
       double speedPrice = Double.parseDouble(driver.findElement(By.xpath("//div[@class='pBox']//div[.='$3,210']")).getText().replaceAll("[$,]", ""));
       
       //24.Select Porsche Ceramic Composite Brakes (PCCB)
       
       JavascriptExecutor jse3 = (JavascriptExecutor) driver;
       jse3.executeScript("window.scrollBy(0,500)", "");
       
       driver.findElement(By.xpath("//span[@id='vs_table_IMG_x_M450_x_c91_M450']")).click();
       
       double ceramicPrice = Double.parseDouble(driver.findElement(By.xpath("//div[@class='pBox']//div[.='$7,410']")).getText().replaceAll("[$,]", ""));
    		   
       	
       
       //25.Verify that Price for Equipment is the sum of 
       //Miami Blue price + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package + 
       //Interior Trim in Carbon Fiber i.c.w. Standard Interior + 7-speed Porsche Doppelkupplung (PDK) + Porsche Ceramic Composite Brakes (PCCB)
       
       ePrice = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='ccaPrice'][contains(text(),'$20,820')])[2]")).getText().replaceAll("[$,]", ""));
       
       Assert.assertEquals(ePrice, miamiPrice + wheelPrice + seatPrice + intTrimPrice + speedPrice + ceramicPrice);
       
       //26.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
       
       totalPrice = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='ccaPrice'][contains(text(),'$78,770')])[2]")).getText().replaceAll("[$,]", ""));
       
       
       Assert.assertEquals(totalPrice, basePrice + deliveryPrice + ePrice); 
       
       
       
       
       
       
       
        
        
        
        
        
        		
        
        
        
        
		
		
		
		

		
		
	}

}
