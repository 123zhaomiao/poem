import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Test {
    public static void main(String[] args) throws Exception {
        submittingForm();
    }
    public static void submittingForm() throws Exception {
        try (final WebClient webClient = new WebClient()) {

            // Get the first page
            final HtmlPage page1 = webClient.getPage("http://some_url");

            // Get the form that we are dealing with and within that form,
            // find the submit button and the field that we want to change.
            final HtmlForm form = page1.getFormByName("myform");

            final HtmlSubmitInput button = form.getInputByName("submitbutton");
            final HtmlTextInput textField = form.getInputByName("userid");

            // Change the value of the text field
            textField.type("root");

            // Now submit the form by clicking the button and get back the second page.
            final HtmlPage page2 = button.click();
        }
    }
}
