package  com.jy.framework.core.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * XML工具类
 * @author zhanglj
 */
public class XmlUtil {
	
	/**
	 * xml字符串转Map
	 * @param xmlStr
	 * @return
	 */
	public static Map<String, String> xml2Map(String xmlStr)throws JDOMException, IOException {
		Map<String, String> rtnMap = new HashMap<String, String>();
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new StringReader(xmlStr));
		// 得到根节点  
		Element root = doc.getRootElement();
		String rootName = root.getName();
		rtnMap.put(rootName, rootName);
		// 调用递归函数，得到所有最底层元素的名称和值，加入map中  
		convert(root, rtnMap);
		return rtnMap;
	}
	
	/**
	 * 递归函数，找出最下层的节点并加入到map中，由xml2Map方法调用。
	 * @param e  xml节点，包括根节点
	 * @param map 目标map
	 * @param lastname 从根节点到上一级节点名称连接的字串
	 */
	public static void convert(Element e, Map<String, String> map) {
		if (e.getAttributes().size() > 0) {
			Iterator it_attr = e.getAttributes().iterator();
			while (it_attr.hasNext()) {
				Attribute attribute = (Attribute) it_attr.next();
				String attrname = attribute.getName();
				String attrvalue = e.getAttributeValue(attrname);
				map.put(attrname, attrvalue);
			}
		}
		List children = e.getChildren();
		Iterator it = children.iterator();
		while (it.hasNext()) {
			Element child = (Element) it.next();
			String name = child.getName();
			// 如果有子节点，则递归调用
			if (child.getChildren().size() > 0) {
				convert(child, map);
			} else {
				// 如果没有子节点，则把值加入map
				map.put(name, child.getText());
				// 如果该节点有属性，则把所有的属性值也加入map
				if (child.getAttributes().size() > 0) {
					Iterator attr = child.getAttributes().iterator();
					while (attr.hasNext()) {
						Attribute attribute = (Attribute) attr.next();
						String attrname = attribute.getName();
						String attrvalue = child.getAttributeValue(attrname);
						map.put(attrname, attrvalue);
					}
				}
			}
		}
	}
	
	/**
	 * 中国银行查询响应项解析：xml字符串转List<Map>
	 * @param xmlStr 
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static List<Map<String, String>> xml2List(String xmlStr)throws JDOMException, IOException {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new StringReader(xmlStr));
		// 得到根节点  
		Element root = doc.getRootElement();
		List children = root.getChildren();
		Iterator it = children.iterator();
		String responseCode = "";
		String responseInfo = "";		
		
		while (it.hasNext()) {
			Element child = (Element) it.next();
			if(child.getName().equals("head")){
				List childs = child.getChildren();
				Iterator its = childs.iterator();
				while (its.hasNext()) {
					Element c = (Element) its.next();
					if(c.getName().equals("responseCode")){
						responseCode = c.getText();
					}else if(c.getName().equals("responseInfo")){
						responseInfo = c.getText();
					}
				}
			}else{
				List childs = child.getChildren();
				Iterator its = childs.iterator();
				while (its.hasNext()) {
					Map<String, String> rtnMap = new HashMap<String, String>();
					rtnMap.put("responseCode", responseCode);
					rtnMap.put("responseInfo", responseInfo);
					Element c = (Element) its.next();
					convertSimple(c,rtnMap);
					list.add(rtnMap);
				}
			}
		}	
		return list;
	}

	private static void convertSimple(Element e, Map<String, String> map) {
		List children = e.getChildren();
		Iterator it = children.iterator();
		while (it.hasNext()) {
			Element child = (Element) it.next();
			String name = child.getName();
			map.put(name, child.getText());
		}
	}
	
	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><response><head>"
						+"<responseCode>OK</responseCode>"
						+"<responseInfo>成功</responseInfo>"
						+"</head>"
						+"<body>"
						+"<order>"
						+"<merchantNo>6936</merchantNo>"
						+"<orderNo>20150814131104000002</orderNo>"
						+"<orderSeq>112008672</orderSeq>"
						+"<currency>156</currency>"
						+"<amount>4390</amount>"
						+"<lastTranTime></lastTranTime>"
						+"<refundedAmount>0</refundedAmount>"
						+"<orderStatus>0</orderStatus>"
						+"<payeeActNum></payeeActNum>"
						+"<payeeActNam></payeeActNam>"
						+"<bocFlag></bocFlag>"
						+"<payeeBnkOrg></payeeBnkOrg>"
						+"<payeeCnaps></payeeCnaps>"
						+"<payeeIbknum></payeeIbknum>"
						+"<payeeBnkNam></payeeBnkNam>"
						+"<orderType>0</orderType>"
						+"<payCatalog>1</payCatalog>"
						+"</order>"
						+"<order>"
						+"<merchantNo>6936111</merchantNo>"
						+"<orderNo>20150814131104000002</orderNo>"
						+"<orderSeq>112008672</orderSeq>"
						+"<currency>156</currency>"
						+"<amount>4390</amount>"
						+"<lastTranTime></lastTranTime>"
						+"<refundedAmount>022</refundedAmount>"
						+"<orderStatus>0222</orderStatus>"
						+"<payeeActNum></payeeActNum>"
						+"<payeeActNam></payeeActNam>"
						+"<bocFlag></bocFlag>"
						+"<payeeBnkOrg></payeeBnkOrg>"
						+"<payeeCnaps></payeeCnaps>"
						+"<payeeIbknum></payeeIbknum>"
						+"<payeeBnkNam></payeeBnkNam>"
						+"<orderType>0</orderType>"
						+"<payCatalog>1</payCatalog>"
						+"</order>"
						+"</body>"
						+"</response>";
		
//		Map<String, String> map = new HashMap<String, String>();
		
		try {
//			map = xml2Map(xml);
			List<Map<String,String>> list = xml2List(xml);
			for (Map<String,String> map : list) {
				for (String key : map.keySet()) {
					System.out.println(key+"="+map.get(key));
				}
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

