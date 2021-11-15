package GeneratorPDFFile;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import ConfigurationFileReader.PropertyLoader;
import DTO.Article;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;

public class GeneratorPDFFile {

    private Properties prop=null;

    public boolean searchToCert(Article article) throws IOException {
        boolean find = false;
        setProp(getProp());
        return new File(getProp().getProperty("pathCache") + article.getNom()+".pdf").exists();
    }

    public void generatedPDFFile(Article article) throws IOException {

        setProp(getProp());
        //generate a PDF at the specified location
        System.out.println("AVANT PDFWriter");
        PdfWriter writer = new PdfWriter(getProp().getProperty("pathCache")+ article.getNom()+".pdf");
        System.out.println("APRES PDFWriter");

        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document
        Document doc = new Document(pdf);

        // Creating an ImageData object
        System.out.println("AVANT Image");
        ImageData data = ImageDataFactory.create(getProp().getProperty("imageFile"));
        System.out.println("APRES Image");

        // Creating an Image object
        Image img = new Image(data);

        // Setting the position of the image to the center of the page
        img.setMaxHeight(150);

        // Adding image to the document
        doc.add(img);

        // Creating text object
        Text text1 = new Text("Certification de conformité du produit : " + article.getNom());

        // Setting font of the text

        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        text1.setFont(font);

        // Setting font size
        text1.setFontSize(18);

        // Creating Paragraph
        Paragraph paragraph1 = new Paragraph();

        // Adding text1 to the paragraph
        paragraph1.add(text1);

        //adds paragraph to the PDF file
        doc.add(paragraph1);

        //close the PDF file
        doc.close();
        //closes the writer
        writer.close();
    }

    public void setProp(Properties prop) {
           // Load du fichier properties
            prop = PropertyLoader.load("ServeurCertificat\\prop.txt");
            this.prop = prop;
    }

    public Properties getProp() {
        return prop;
    }
}
