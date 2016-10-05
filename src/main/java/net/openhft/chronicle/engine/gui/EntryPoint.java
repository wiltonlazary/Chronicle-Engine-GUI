package net.openhft.chronicle.engine.gui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import net.openhft.chronicle.engine.tree.VanillaAssetTree;

import javax.servlet.annotation.WebServlet;


/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@SuppressWarnings("WeakerAccess")
@Theme("mytheme")
public class EntryPoint extends UI {

    private final VanillaAssetTree tree = SimpleEngine.createEngine();
    private final UserUiManager userUIManager = new UserUiManager();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new MainUiManager().newComponent(vaadinRequest));
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = EntryPoint.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }


}
