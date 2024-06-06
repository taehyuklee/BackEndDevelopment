package mock.server.user.domain.entity;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.xml.transform.Result;
import java.io.StringWriter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "userInfo")
@NoArgsConstructor //이거 있어야 구현이 가능하다.
@Slf4j
public class UserInfo {

//    @XmlElement(name = "id")
    private Long id;

//    @XmlElement(name = "name")
    private String name;

//    @XmlElement(name = "pwd")
    private String pwd;

    public UserInfo(String name, String pwd){
        this.name = name;
        this.pwd = pwd;
    }

    @Override
    public String toString(){
        return "name: " + this.name + " pwd: " + this.pwd;
    }

    public String xmlConvert() throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(UserInfo.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(this, stringWriter);

        String xmlContent = stringWriter.toString();

        log.info("xmlContent:{}", xmlContent);
        return xmlContent;

    }

}
