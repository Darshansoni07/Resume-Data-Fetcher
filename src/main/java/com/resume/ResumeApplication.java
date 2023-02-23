package com.resume;



import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResumeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResumeApplication.class, args);
		try (PDDocument document = PDDocument.load(new File("Resume.pdf"))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            String name = extractName(text);
            String email = extractEmail(text);
            String phone = extractPhone(text);
            String education = extractEducation(text);
            String experience = extractExperience(text);

            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Phone: " + phone);
            System.out.println("Education: " + education);
            System.out.println("Experience: " + experience);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extractName(String text) {
        // Match name pattern (first and last name)
        Pattern pattern = Pattern.compile("(\\b[A-Z][a-z]+\\b)\\s(\\b[A-Z][a-z]+\\b)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    private static String extractEmail(String text) {
        // Match email pattern
        Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    private static String extractPhone(String text) {
        // Match phone number pattern
        Pattern pattern = Pattern.compile("\\b\\d{3}[.-]\\d{3}[.-]\\d{4}\\b");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    private static String extractEducation(String text) {
        // Match education section (degree and institution)
        Pattern pattern = Pattern.compile("Education\\s*(.*?)\\s*Experience", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private static String extractExperience(String text) {
        // Match experience section (job title, company, dates, and description)
        Pattern pattern = Pattern.compile("Experience\\s*(.*?)\\s*\\n\\b[A-Z][a-z]+\\b", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
		
}
