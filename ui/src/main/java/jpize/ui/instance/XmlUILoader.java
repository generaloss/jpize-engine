package jpize.ui.instance;

import jpize.util.file.Resource;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;


public class XmlUILoader{

    public static UIInstance load(Resource resource){
        try{
            final Element element = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(resource.file())
                    .getDocumentElement();

            System.out.println(element.getTagName());


        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
