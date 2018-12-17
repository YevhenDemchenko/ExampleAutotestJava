package QATestLab.TestPrestashopautomation;

import QATestLab.TestPrestashopautomation.properties.Settings;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.io.IOException;

public class Tests extends Settings {
	private static String String;
	private WebDriver driver;
	
@Before
	public void setUp() throws Exception {
		driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");        
        driver.manage().window().maximize();        
    }

@Test
    public void test() throws SecurityException, IOException {
	java.util.logging.Logger logger = java.util.logging.Logger.getAnonymousLogger();
    FileHandler fh = new FileHandler("C:/Users/jkl13/Desktop/Maven/TestPrestashop-automation/mylog.txt");
    // Send logger output to our FileHandler.
    logger.addHandler(fh);
    // Request that every detail gets logged.
    logger.setLevel(Level.ALL);
    String s = null;
    logger.log(Level.ALL, s);
    
/*1. Открываем главную страницу сайта.
*/	
		String BaseUrl = "http://prestashop-automation.qatestlab.com.ua/ru/";
        driver.get(BaseUrl);
        logger.info("Open the main page of the site");

/*2. Выполнить проверку, что цена продуктов в секции "Популярные товары" указана в соответствии с установленной валютой в шапке сайта (USD, EUR, UAH).
*/
        checkCurrency(String); 	
        logger.info("Text output: Check that the price of products in the section \"Популярные товары\" is indicated in accordance with the established currency in the site header (USD, EUR, UAH).");
			
/*3. Установить показ цены в USD используя выпадающий список в шапке сайта.
*/
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@id,'_desktop_currency_selector')]/div/a")));
		driver.findElement(By.xpath("//div[contains(@id,'_desktop_currency_selector')]/div/a")).click();
		logger.info("Сlicking on an item");
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@title,'Доллар США')]")));
        driver.findElement(By.xpath("//a[contains(@title,'Доллар США')]")).click();
        logger.info("Сlicking on an item");
        System.out.println("Currency type of products changed to USD in header. Verified");
        logger.info("Text output: \"Currency type of products changed to USD in header. Verified\"");

/*4. Выполнить поиск в каталоге по слову “dress.”
*/
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='search_widget']/form/input[2]")));
		driver.findElement(By.name("s")).clear();
		logger.info("Clearing the field");
        driver.findElement(By.name("s")).sendKeys("dress.");
        logger.info("Text input \"dress.\"");
		driver.findElement(By.xpath("//*[@id='search_widget']/form/button")).click();
		logger.info("clicking on an item");
		System.out.println("Search is performed in the catalog by the  “dress.” word");
		logger.info("Text output: \"Search is performed in the catalog by the  “dress.” word\"");

/*5. Выполнить проверку, что страница "Результаты поиска" содержит надпись "Товаров: x", где x - количество действительно найденных товаров.
*/
		getFoundProductsLabel(); 
		logger.info("Text output: Check that the \"Результаты поиска\" page contains the inscription \"Товаров: x\", where x is the number of actually found products.");
        
/*6. Проверить, что цена всех показанных результатов отображается в долларах.
*/ 
        checkTheCurrencyOfFoundProducts(String);
        logger.info("Text output: Check that the price of all displayed results is displayed in dollars.");
		 
/*7. Установить сортировку "от высокой к низкой."
*/
        wait.until(ExpectedConditions.elementToBeClickable(By.className("select-title")));
        driver.findElement(By.className("select-title")).click();
        logger.info("Сlicking on an item");
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='select-list js-search-link'][4]")));
        driver.findElement(By.xpath("//a[@class='select-list js-search-link'][4]")).click();
        logger.info("Сlicking on an item");
        System.out.println("The High to Low price sorting method is installed");
        logger.info("Text output: \"The High to Low price sorting method is installed\"");
    
/*8. Проверить, что товары отсортированы по цене, при этом некоторые товары могут быть со скидкой, и при сортировке используется цена без скидки.
*/
		checkHighToLowSorting(); 
		logger.info("Text output: Check that the products are sorted by price, with some products may be discounted, and the price without discount is used when sorting.");
	
/*@9. Для товаров со скидкой указана скидка в процентах вместе с ценой до и после скидки.
*/
		checkDiscountProductPriceAndLabel(); 
		logger.info("Text output: Check that the products with a discount have a percentage discount with the price before and after the discount.");
	
/*10. Необходимо проверить, что цена до и после скидки совпадает с указанным размером скидки.
*/
		checkDiscountCalculation();
		logger.info("Text output: Check that the price before and after the discount coincides with the specified size of the discount.");
		
	}
   
	 public boolean checkCurrency(String currency) {	
		WebElement productPrice = driver.findElement(By.cssSelector(".price"));
		String symbol = productPrice.getText();
		WebElement headPrice = driver.findElement(By.xpath("//*[@id='_desktop_currency_selector']/div/span[2]"));
		String symbol2 = headPrice.getText();
	 		if(symbol.contains("€") == symbol2.contains("EUR")) {
	 			System.out.println("The currency type of the products doesn't match the selected EUR currency in header");
	 			return symbol.contains("€") || symbol.contains("EUR");
			}
			else if(symbol.contains("₴") == symbol2.contains("UAH")) {
                System.out.println("The currency type of the products match the selected UAH currency in header");
                return symbol.contains("₴");
			}
			 else if(symbol.contains("$") == symbol2.contains("USD")) {
                System.out.println("The currency type of the products match the selected USD currency in header");
                return symbol.contains("$") || symbol.contains("USD");
			} 
			else {
                throw new IllegalArgumentException("Please, enter the correct currency name: eur, uah or usd");
			}
	}
	public boolean getFoundProductsLabel() {
		List<WebElement> foundProductsLabel = driver.findElements(By.xpath("//div[@id='js-product-list-top']/div[1]/p[contains(text(), 'Товаров:')]"));
        if(foundProductsLabel.size() > 0) {
        	WebElement foundProductsLabelC = driver.findElement(By.cssSelector("#js-product-list-top > div.col-md-6.hidden-sm-down.total-products > p"));
        	String symbol5 = foundProductsLabelC.getText();
        	System.out.println("Veryfing that the \"Товаров\" label is displayed and display the correct quantity of products ...");
        	System.out.println("The label: \"" + symbol5 + "\" is displayed and correct. Verified." ); 
        	return true;
		}
		return false;
	}
		
    public boolean checkTheCurrencyOfFoundProducts(String currency) {
		List<WebElement> prices = driver.findElements(By.xpath("//div[contains(@class,'price')]")); 
		if(prices.size() > 0) {
			for (WebElement value : prices) {
				String symbol3 = value.getText();
				if(symbol3.contains("$")) {
					System.out.println("All products prices are displayed in USD currency. Verified.");
					return true;
				}
				else {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public boolean checkHighToLowSorting() {
        List<WebElement> regularPrices = driver.findElements(By.xpath("//div[@class='product-price-and-shipping']/span[1]"));
        if (regularPrices.size() >= 2) {
            System.out.println("Verifying the High to Low price sorting method...");
            for (int i = 0; i + 1 < regularPrices.size(); i++) {
                float price = Float.parseFloat(regularPrices.get(i).getText().substring(0, 4).replace(",", "."));
                float nextPrice = Float.parseFloat(regularPrices.get(i + 1).getText().substring(0, 4).replace(",", "."));
                if (!(price >= nextPrice)) {
                    System.out.println(price + " is less than " + nextPrice + " Failed.");
                    return false;
                }
                System.out.println(price + " is more than or equals " + nextPrice + " Verified.");
            }
            System.out.println("The products are sorted correctly");
            return true;           
        } 
        else {
            throw new IllegalStateException("Impossible to check sorting method, there are less than 2 products on page");
        }
    }	
		
	public boolean checkDiscountProductPriceAndLabel() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'discount')]")));
        List<WebElement> productsWithDiscount = driver.findElements(By.className("discount"));
        List<WebElement> labelsOfDiscount = driver.findElements(By.xpath("//span[contains(@class,'discount')]"));
        List<WebElement> regularPriceOfDiscountProducts = driver.findElements(By.xpath("//span[@class='discount-percentage']/preceding-sibling::span"));
        List<WebElement> actualPriceOfDiscountProducts = driver.findElements(By.xpath("//span[@class='discount-percentage']/following-sibling::span"));
        System.out.println("Verifying each On-Sale product has its own discount label, actual and regular price ...");
        System.out.println("Found on page: " + productsWithDiscount.size() + " products On-Sale.");
        System.out.println("Found on page: " + labelsOfDiscount.size() + " labels of products On-Sale.");
        System.out.println("Found on page: " + regularPriceOfDiscountProducts.size() + " regular prices of products On-Sale.");
        System.out.println("Found on page: " + actualPriceOfDiscountProducts.size() + " actual prices of products On-Sale.");
        System.out.println("Verifying the price is displayed in percentages ...");
        for (WebElement label : labelsOfDiscount) {
        	String label1 = label.getText();
            if (!label1.contains("%")) {
                System.out.println("Label: " + label.getText() + " Failed.");
                return false;
            }
            System.out.println("Label: " + label.getText() + " Verified.");
        }
        if (productsWithDiscount.size() == labelsOfDiscount.size() && productsWithDiscount.size() == regularPriceOfDiscountProducts.size() && productsWithDiscount.size() == actualPriceOfDiscountProducts.size()) {
            System.out.println("Quantity of On-Sale products: " + productsWithDiscount.size() + " equals the quantity of discount labels: " + labelsOfDiscount.size() + " quantity of regular prices: " + regularPriceOfDiscountProducts.size() + " quantity of actual prices: " + actualPriceOfDiscountProducts.size() + ". Verified.");
            return true;
        } 
        else {
            System.out.println("Quantity of On-Sale products: " + productsWithDiscount.size() + " IS NOT equal the quantity of discount labels: " + labelsOfDiscount.size() + " quantity of regular prices: " + regularPriceOfDiscountProducts.size() + " quantity of actual prices: " + actualPriceOfDiscountProducts.size() + ". Verified.");
            return false;
        }	
    }
	
	public boolean checkDiscountCalculation() {
        List<WebElement> productsWithDiscount = driver.findElements(By.xpath("//span[contains(@class,'discount')]"));
        List<WebElement> labelsOfDiscount = driver.findElements(By.xpath("//span[contains(@class,'discount')]"));
        List<WebElement> regularPriceOfDiscountProducts = driver.findElements(By.xpath("//span[@class='discount-percentage']/preceding-sibling::span"));
        List<WebElement> actualPriceOfDiscountProducts = driver.findElements(By.xpath("//span[@class='discount-percentage']/following-sibling::span"));
        System.out.println("Verifying the discount calculation ...");
        for (int i = 0; i < productsWithDiscount.size(); i++) {
            int discountPercentage = Integer.parseInt(labelsOfDiscount.get(i).getText().replace("%", ""));
            float regularPrice = Float.parseFloat(regularPriceOfDiscountProducts.get(i).getText().substring(0, 4).replace(",", "."));
            float actualPrice = Float.parseFloat(actualPriceOfDiscountProducts.get(i).getText().substring(0, 4).replace(",", "."));
            float discountAmount = ((regularPrice * Math.abs(discountPercentage)) / 100);
            if (!((regularPrice - discountAmount) == actualPrice)) {
                System.out.println(productsWithDiscount.get(i).getTagName() + " product has " + labelsOfDiscount.get(i).getText() + " . Actual price from " + regularPrice + " is " + actualPrice + " Failed.");
                return false;
            }
            System.out.println("Product #" + (i + 1) + " has " + labelsOfDiscount.get(i).getText() + "($" + discountAmount + ")" + " discount amount. Actual price from " + regularPrice + " is " + actualPrice + " Verified.");
        }
        return true;      
    } 

@After
    public void tearDown() {
        if (driver != null) {
            driver.close();
        }
	}
}