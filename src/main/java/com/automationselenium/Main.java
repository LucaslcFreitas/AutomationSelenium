package com.automationselenium;

import java.io.IOException;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.WebElement;
/**
 *
 * @author Lucas
 */
public class Main {
     public static String months[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
     public static String years[] = {"2017", "2018", "2019", "2020", "2021"};
     public static int start_period[] = {0, 2};
     public static int end_period[] = {11, 3};
     public static String local = "JUIZ DE FORA";
    
    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.gecko.driver", "local");
        WebDriver browser = new FirefoxDriver();
        int count = 0, current_month = start_period[0], current_year = start_period[1];
        
        int lengthYears = (end_period[1] - start_period[1]) + 1;
        String labels[] = new String[lengthYears];
        for(int k=0; k<lengthYears; k++)
            labels[k] = years[start_period[1] + k];
        Data data = new Data(labels, lengthYears);
        
        browser.get("https://www.detran.mg.gov.br/parceiros-credenciados/centro-de-formacao-de-condutores/consultar-produtividade-dos-cfcs");
        
        while(current_month != end_period[0] || current_year != end_period[1]) {
            //Seleciona a cidade
            Select city = new Select(browser.findElement(By.name("cidade")));
            city.selectByVisibleText(local);

            //Seleciona o período inicial
            Select startPeriod = new Select(browser.findElement(By.name("periodo_inicio")));
            //startPeriod.selectByVisibleText(months[current_month]+"/"+years[current_year]);

            //Seleciona o período final
            Select endPeriod = new Select(browser.findElement(By.name("periodo_fim")));
            //endPeriod.selectByVisibleText(months[current_month]+"/"+years[current_year]);

            if(SelectElementContainsItemText(startPeriod, months[current_month]+"/"+years[current_year]) && SelectElementContainsItemText(endPeriod, months[current_month]+"/"+years[current_year])) {
                startPeriod.selectByVisibleText(months[current_month]+"/"+years[current_year]);
                endPeriod.selectByVisibleText(months[current_month]+"/"+years[current_year]);
 
                //Tipo de consulta
                browser.findElement(By.id("tipo-consulta-d")).click();

                //Seleciona o tipo de exame
                Select typeExam = new Select(browser.findElement(By.name("tipo_exame")));
                typeExam.selectByValue("2");

                browser.findElement(By.id("pesquisar")).click();


                List<WebElement> row = browser.findElements(By.xpath("//table/tbody/tr/td[1]"));
                
                System.out.println(months[current_month]+"/"+years[current_year]);
                System.out.println("Linhas: " + row.size());

                if(row.size() > 0) {
                    for(int i=1; i<=row.size(); i++) {
                        String name = browser.findElement(By.xpath("//table/tbody/tr["+i+"]/td[3]")).getText();
                        float percent = Float.parseFloat(browser.findElement(By.xpath("//table/tbody/tr["+i+"]/td[4]")).getText().replace("%", "").trim());
                        data.setData(name, current_year-start_period[1], current_month, percent);
                    }
                }

                browser.findElement(By.xpath("//html/body/main/div/div/div/div[1]/div/div[3]/div/div[2]/div[2]/div/a")).click();
            }
            
            count = count + 1;
            current_month = current_month + 1;
            if(current_month == 12) {
                current_month = 0;
                current_year = current_year + 1;
            }
        }
        
        data.printData();
        
        browser.quit();
        
        SaveToExcel save = new SaveToExcel(data.getYearsData());
        save.saveFile("dados.xlsx");
    }
    
    public static boolean SelectElementContainsItemText(Select element, String itemText) {
        boolean found = false;

        for (int i = 0; i < element.getOptions().size(); i++)
        {
            String blah =  element.getOptions().get(i).getText();
            if (blah.equals(itemText))
            {
                found = true;
                break;
            }
        }

        return found;
    }
}