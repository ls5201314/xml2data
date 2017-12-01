package com.emat.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.emat.util.DateUtil;
import com.emat.util.UUIDUtil;
import com.emat.util.XmlUtil;
import com.emat.xml.XmlEntity;

public class xml2data {
	private static final String SEMICOLON = ";";

	public static void parseXml2Data(File doc, File desFile) {
		// 中文会议文献
		// c01_ns02(doc, desFile);

		// 英文会议文献
		// c02_ns01(doc, desFile);

		// 中文学位论文
		// d01_ns08(doc, desFile);

		// 英文学位论文
		// d02_ns09(doc, desFile);

		// 西文期刊
		// j02_ns04(doc, desFile);

		// 俄文期刊
		//j03_ns05(doc, desFile);
		
		// 日文期刊
		//j04_ns06(doc, desFile);
		
		// 英文科技报告
		//r02_ns07(doc, desFile);
		
		// 中文期刊
		z01_ns03(doc, desFile);

	}

	// 中文会议文献
	public static void c01_ns02(File doc, File desFile) {

		final String REFERENCE = "doc_id";
		final String PRE_REFERENCE = "ns02";
		final String DRECONTENT = "abstract";
		final String DREDATE = "create_time";
		Map<String, String> normalMap = new HashMap<>();
		normalMap.put("title", "DRETITLE");

		Map<String, String> equalMap = new HashMap<>();
		equalMap.put("doc_id", "DOC_ID");
		equalMap.put("paper_id", "PAPER_ID");
		equalMap.put("classification", "CLASSIFICATION");
		equalMap.put("language", "LANGUAGE");
		equalMap.put("start_page", "START_PAGE");
		equalMap.put("end_page", "END_PAGE");
		equalMap.put("total_page_number", "TOTAL_PAGE_NUMBER");
		equalMap.put("type", "TYPE");
		equalMap.put("host_title", "HOST_TITLE");
		equalMap.put("conference_name", "CONFERENCE_NAME");
		equalMap.put("conference_date", "CONFERENCE_DATE");
		equalMap.put("conference_place", "CONFERENCE_PLACE");
		equalMap.put("conference_sponsor", "CONFERENCE_SPONSOR");
		equalMap.put("processing_unit", "PROCESSING_UNIT");
		equalMap.put("library_code", "LIBRARY_CODE");
		equalMap.put("holding_number", "HOLDING_NUMBER");

		Map<String, String> listMap = new HashMap<>();
		listMap.put("keyword", "KEYWORD");
		listMap.put("author_name", "AUTHOR");
		listMap.put("affiliation", "AFFILIATION");

		// 常量
		StringBuffer constantValue = new StringBuffer();
		constantValue.append("#DREFIELD ISBN=\"\"\n");
		constantValue.append("#DREFIELD ISSN=\"\"\n");
		constantValue.append("#DREFIELD PUBLISHER=\"\"\n");
		constantValue.append("#DREFIELD CONFERENCE_NO=\"\"\n");
		constantValue.append("#DREFIELD IMPORTMAGICEXTENSION=\"idx\"\n");
		constantValue.append("#DREFIELD IMPORTMAGICFRIENDLYTYPE=\"text/plain\"\n");
		constantValue.append("#DREFIELD DOCUMENTCLASS=\"1\"\n");
		constantValue.append("#DREFIELD DOCUMENTTYPE=\"2\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_VERSION=\"0\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_ATTRIBUTES=\"0\"\n");
		constantValue.append("#DRESECTION 0\n");
		constantValue.append("#DREDBNAME HG_HX_NS02\n#DREENDDOC\n\n");
		Writer w = null;
		try {
			FileOutputStream fos = new FileOutputStream(desFile);
			w = new OutputStreamWriter(fos, "utf-8");
			XmlEntity xmlEntity = XmlUtil.parseResponseString(new String(FileUtils.readFileToByteArray(doc)));
			XmlEntity entity = xmlEntity.findFirstEnclosedOccurrence("paper");
			StringBuffer rowValue = new StringBuffer();
			XmlEntity childEntity = null;
			while (null != entity) {
				childEntity = entity.child();
				rowValue = new StringBuffer();
				getNormalMapValue(normalMap, rowValue, childEntity);
				getEqualMapValue(equalMap, rowValue, childEntity);
				getListMapValue(listMap, rowValue, childEntity);
				getTimeValue(DREDATE, rowValue, childEntity);
				getContentValue(DRECONTENT, rowValue, childEntity);
				w.write(additionalReference(PRE_REFERENCE, REFERENCE, childEntity));
				w.write(rowValue.toString());
				w.write(constantValue.toString());
				entity = entity.next();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 英文会议文献
	public static void c02_ns01(File doc, File desFile) {

		final String REFERENCE = "doc_id";
		final String PRE_REFERENCE = "ns01";
		final String DRECONTENT = "abstract";
		final String DREDATE = "create_time";
		Map<String, String> normalMap = new HashMap<>();
		normalMap.put("title", "DRETITLE");

		Map<String, String> equalMap = new HashMap<>();
		equalMap.put("doc_id", "DOC_ID");
		equalMap.put("paper_id", "PAPER_ID");
		equalMap.put("classification", "CLASSIFICATION");
		equalMap.put("language", "LANGUAGE");
		equalMap.put("start_page", "START_PAGE");
		equalMap.put("end_page", "END_PAGE");
		equalMap.put("total_page_number", "TOTAL_PAGE_NUMBER");
		equalMap.put("type", "TYPE");
		equalMap.put("host_title", "HOST_TITLE");
		equalMap.put("conference_name", "CONFERENCE_NAME");
		equalMap.put("conference_date", "CONFERENCE_DATE");
		equalMap.put("conference_place", "CONFERENCE_PLACE");
		equalMap.put("conference_no", "CONFERENCE_NO");
		equalMap.put("conference_sponsor", "CONFERENCE_SPONSOR");
		equalMap.put("processing_unit", "PROCESSING_UNIT");
		equalMap.put("library_code", "LIBRARY_CODE");
		equalMap.put("holding_number", "HOLDING_NUMBER");

		Map<String, String> listMap = new HashMap<>();
		listMap.put("keyword", "KEYWORD");
		listMap.put("author_name", "AUTHOR");
		listMap.put("affiliation", "AFFILIATION");

		// 常量
		StringBuffer constantValue = new StringBuffer();
		constantValue.append("#DREFIELD ISBN=\"\"\n");
		constantValue.append("#DREFIELD ISSN=\"\"\n");
		constantValue.append("#DREFIELD PUBLISHER=\"\"\n");
		constantValue.append("#DREFIELD IMPORTMAGICEXTENSION=\"idx\"\n");
		constantValue.append("#DREFIELD IMPORTMAGICFRIENDLYTYPE=\"text/plain\"\n");
		constantValue.append("#DREFIELD DOCUMENTCLASS=\"1\"\n");
		constantValue.append("#DREFIELD DOCUMENTTYPE=\"2\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_VERSION=\"0\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_ATTRIBUTES=\"0\"\n");
		constantValue.append("#DRESECTION 0\n");
		constantValue.append("#DREDBNAME HG_HX_NS01\n#DREENDDOC\n\n");
		Writer w = null;
		try {
			FileOutputStream fos = new FileOutputStream(desFile);
			w = new OutputStreamWriter(fos, "utf-8");
			XmlEntity xmlEntity = XmlUtil.parseResponseString(new String(FileUtils.readFileToByteArray(doc)));
			// XmlEntity entity =
			// xmlEntity.findFirstEnclosedOccurrence("paper");
			XmlEntity entity = xmlEntity.findFirstEnclosedOccurrence("record");
			StringBuffer rowValue = new StringBuffer();
			XmlEntity childEntity = null;
			while (null != entity) {
				childEntity = entity.child();
				rowValue = new StringBuffer();
				getNormalMapValue(normalMap, rowValue, childEntity);
				getEqualMapValue(equalMap, rowValue, childEntity);
				getListMapValue(listMap, rowValue, childEntity);
				getTimeValue(DREDATE, rowValue, childEntity);
				getContentValue(DRECONTENT, rowValue, childEntity);
				w.write(additionalReference(PRE_REFERENCE, REFERENCE, childEntity));
				w.write(rowValue.toString());
				w.write(constantValue.toString());
				entity = entity.next();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 中文学位论文
	public static void d01_ns08(File doc, File desFile) {

		final String REFERENCE = "doc_id";
		final String PRE_REFERENCE = "ns08";
		final String DRECONTENT = "abstract";
		final String DREDATE = "create_time";
		Map<String, String> normalMap = new HashMap<>();
		normalMap.put("title", "DRETITLE");

		Map<String, String> equalMap = new HashMap<>();
		equalMap.put("doc_id", "DOC_ID");
		equalMap.put("paper_id", "PAPER_ID");
		equalMap.put("classification", "CLASSIFICATION");
		equalMap.put("submit_date", "SUBMIT_DATE");
		equalMap.put("total_page_number", "TOTAL_PAGE_NUMBER");
		equalMap.put("degree", "DEGREE");
		equalMap.put("major", "MAJOR");
		equalMap.put("university", "UNIVERSITY");
		equalMap.put("degree_offering_org", "DEGREE_OFFERING_ORG");
		equalMap.put("type", "TYPE");
		equalMap.put("processing_unit", "PROCESSING_UNIT");
		equalMap.put("library_code", "LIBRARY_CODE");
		equalMap.put("holding_number", "HOLDING_NUMBER");

		Map<String, String> listMap = new HashMap<>();
		listMap.put("keyword", "KEYWORD");
		listMap.put("author_name", "AUTHOR");
		listMap.put("affiliation", "AFFILIATION");
		listMap.put("supervisor_name", "SUPERVISOR");
		listMap.put("supervisor_affiliation", "SUPERVISOR_AFFILIATION");

		// 常量
		StringBuffer constantValue = new StringBuffer();
		constantValue.append("#DREFIELD ISBN=\"\"\n");
		constantValue.append("#DREFIELD RESEARCH_SUBJECT=\"\"\n");
		constantValue.append("#DREFIELD ALTERNATIVE=\"\"\n");
		constantValue.append("#DREFIELD IMPORTMAGICEXTENSION=\"idx\"\n");
		constantValue.append("#DREFIELD IMPORTMAGICFRIENDLYTYPE=\"text/plain\"\n");
		constantValue.append("#DREFIELD DOCUMENTCLASS=\"1\"\n");
		constantValue.append("#DREFIELD DOCUMENTTYPE=\"2\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_VERSION=\"0\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_ATTRIBUTES=\"0\"\n");
		constantValue.append("#DRESECTION 0\n");
		constantValue.append("#DREDBNAME HG_HX_NS08\n#DREENDDOC\n\n");
		Writer w = null;
		try {
			FileOutputStream fos = new FileOutputStream(desFile);
			w = new OutputStreamWriter(fos, "utf-8");
			XmlEntity xmlEntity = XmlUtil.parseResponseString(new String(FileUtils.readFileToByteArray(doc)));
			// XmlEntity entity =
			// xmlEntity.findFirstEnclosedOccurrence("paper");
			XmlEntity entity = xmlEntity.findFirstEnclosedOccurrence("record");
			StringBuffer rowValue = new StringBuffer();
			XmlEntity childEntity = null;
			while (null != entity) {
				childEntity = entity.child();
				rowValue = new StringBuffer();
				getNormalMapValue(normalMap, rowValue, childEntity);
				getEqualMapValue(equalMap, rowValue, childEntity);
				getListMapValue(listMap, rowValue, childEntity);
				getTimeValue(DREDATE, rowValue, childEntity);
				getContentValue(DRECONTENT, rowValue, childEntity);
				w.write(additionalReference(PRE_REFERENCE, REFERENCE, childEntity));
				w.write(rowValue.toString());
				w.write(constantValue.toString());
				entity = entity.next();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 英文学位论文
	public static void d02_ns09(File doc, File desFile) {

		final String REFERENCE = "doc_id";
		final String PRE_REFERENCE = "NS09";
		final String DRECONTENT = "abstract";
		final String DREDATE = "create_time";
		Map<String, String> normalMap = new HashMap<>();
		normalMap.put("title", "DRETITLE");

		Map<String, String> equalMap = new HashMap<>();
		equalMap.put("doc_id", "DOC_ID");
		equalMap.put("paper_id", "PAPER_ID");
		equalMap.put("alternative", "ALTERNATIVE");
		equalMap.put("classification", "CLASSIFICATION");
		equalMap.put("submit_date", "SUBMIT_DATE");
		equalMap.put("total_page_number", "TOTAL_PAGE_NUMBER");
		equalMap.put("degree", "DEGREE");
		equalMap.put("major", "MAJOR");
		equalMap.put("university", "UNIVERSITY");
		equalMap.put("degree_offering_org", "DEGREE_OFFERING_ORG");
		equalMap.put("isbn", "ISBN");
		equalMap.put("type", "TYPE");
		equalMap.put("processing_unit", "PROCESSING_UNIT");
		equalMap.put("library_code", "LIBRARY_CODE");
		equalMap.put("holding_number", "HOLDING_NUMBER");

		Map<String, String> listMap = new HashMap<>();
		listMap.put("keyword", "KEYWORD");
		listMap.put("author_name", "AUTHOR");
		listMap.put("affiliation", "AFFILIATION");
		listMap.put("supervisor_name", "SUPERVISOR");
		listMap.put("supervisor_affiliation", "SUPERVISOR_AFFILIATION");

		// 常量
		StringBuffer constantValue = new StringBuffer();
		constantValue.append("#DREFIELD RESEARCH_SUBJECT=\"\"\n");
		constantValue.append("#DREFIELD IMPORTMAGICEXTENSION=\"idx\"\n");
		constantValue.append("#DREFIELD IMPORTMAGICFRIENDLYTYPE=\"text/plain\"\n");
		constantValue.append("#DREFIELD DOCUMENTCLASS=\"1\"\n");
		constantValue.append("#DREFIELD DOCUMENTTYPE=\"2\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_VERSION=\"0\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_ATTRIBUTES=\"0\"\n");
		constantValue.append("#DRESECTION 0\n");
		constantValue.append("#DREDBNAME HG_HX_NS09\n#DREENDDOC\n\n");
		Writer w = null;
		try {
			FileOutputStream fos = new FileOutputStream(desFile);
			w = new OutputStreamWriter(fos, "utf-8");
			XmlEntity xmlEntity = XmlUtil.parseResponseString(new String(FileUtils.readFileToByteArray(doc)));
			XmlEntity entity = xmlEntity.findFirstEnclosedOccurrence("paper");
			// XmlEntity entity =
			// xmlEntity.findFirstEnclosedOccurrence("record");
			StringBuffer rowValue = new StringBuffer();
			XmlEntity childEntity = null;
			while (null != entity) {
				childEntity = entity.child();
				rowValue = new StringBuffer();
				getNormalMapValue(normalMap, rowValue, childEntity);
				getEqualMapValue(equalMap, rowValue, childEntity);
				getListMapValue(listMap, rowValue, childEntity);
				getTimeValue(DREDATE, rowValue, childEntity);
				getContentValue(DRECONTENT, rowValue, childEntity);
				w.write(additionalReference(PRE_REFERENCE, REFERENCE, childEntity));
				w.write(rowValue.toString());
				w.write(constantValue.toString());
				entity = entity.next();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 西文期刊
	public static void j02_ns04(File doc, File desFile) {

		final String REFERENCE = "doc_id";
		final String PRE_REFERENCE = "ns04";
		final String DRECONTENT = "abstract";
		final String DREDATE = "create_time";
		Map<String, String> normalMap = new HashMap<>();
		normalMap.put("title", "DRETITLE");

		Map<String, String> equalMap = new HashMap<>();
		equalMap.put("doc_id", "DOC_ID");
		equalMap.put("paper_id", "PAPER_ID");
		equalMap.put("classification", "CLASSIFICATION");
		equalMap.put("language", "LANGUAGE");
		equalMap.put("start_page", "START_PAGE");
		equalMap.put("end_page", "END_PAGE");
		equalMap.put("total_page_number", "TOTAL_PAGE_NUMBER");
		equalMap.put("type", "TYPE");
		equalMap.put("cn", "CN");
		equalMap.put("issn", "ISSN");
		equalMap.put("host_title", "HOST_TITLE");
		equalMap.put("year", "YEAR");
		equalMap.put("volume", "VOLUME");
		equalMap.put("issue", "ISSUE");
		equalMap.put("processing_unit", "PROCESSING_UNIT");
		equalMap.put("library_code", "LIBRARY_CODE");
		equalMap.put("holding_number", "HOLDING_NUMBER");

		Map<String, String> listMap = new HashMap<>();
		listMap.put("keyword", "KEYWORD");
		listMap.put("author_name", "AUTHOR");
		listMap.put("affiliation", "AFFILIATION");

		// 常量
		StringBuffer constantValue = new StringBuffer();
		constantValue.append("#DREFIELD IMPORTMAGICEXTENSION=\"idx\"\n");
		constantValue.append("#DREFIELD IMPORTMAGICFRIENDLYTYPE=\"text/plain\"\n");
		constantValue.append("#DREFIELD DOCUMENTCLASS=\"1\"\n");
		constantValue.append("#DREFIELD DOCUMENTTYPE=\"2\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_VERSION=\"0\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_ATTRIBUTES=\"0\"\n");
		constantValue.append("#DRESECTION 0\n");
		constantValue.append("#DREDBNAME HG_HX_NS04\n#DREENDDOC\n\n");
		Writer w = null;
		try {
			FileOutputStream fos = new FileOutputStream(desFile);
			w = new OutputStreamWriter(fos, "utf-8");
			XmlEntity xmlEntity = XmlUtil.parseResponseString(new String(FileUtils.readFileToByteArray(doc)));
			// XmlEntity entity =
			// xmlEntity.findFirstEnclosedOccurrence("paper");
			XmlEntity entity = xmlEntity.findFirstEnclosedOccurrence("record");
			StringBuffer rowValue = new StringBuffer();
			XmlEntity childEntity = null;
			while (null != entity) {
				childEntity = entity.child();
				rowValue = new StringBuffer();
				getNormalMapValue(normalMap, rowValue, childEntity);
				getEqualMapValue(equalMap, rowValue, childEntity);
				getListMapValue(listMap, rowValue, childEntity);
				getTimeValue(DREDATE, rowValue, childEntity);
				getContentValue(DRECONTENT, rowValue, childEntity);
				w.write(additionalReference(PRE_REFERENCE, REFERENCE, childEntity));
				w.write(rowValue.toString());
				w.write(constantValue.toString());
				entity = entity.next();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 俄文期刊
	public static void j03_ns05(File doc, File desFile) {

		final String REFERENCE = "doc_id";
		final String PRE_REFERENCE = "ns05";
		final String DRECONTENT = "abstract";
		final String DREDATE = "create_time";
		Map<String, String> normalMap = new HashMap<>();
		normalMap.put("title", "DRETITLE");

		Map<String, String> equalMap = new HashMap<>();
		equalMap.put("doc_id", "DOC_ID");
		equalMap.put("paper_id", "PAPER_ID");
		equalMap.put("classification", "CLASSIFICATION");
		equalMap.put("language", "LANGUAGE");
		equalMap.put("other_language", "OTHER_LANGUAGE");
		equalMap.put("start_page", "START_PAGE");
		equalMap.put("end_page", "END_PAGE");
		equalMap.put("total_page_number", "TOTAL_PAGE_NUMBER");
		equalMap.put("total_reference_number", "TOTAL_REFERENCE_NUMBER");
		equalMap.put("type", "TYPE");
		equalMap.put("paper_type", "PAPER_TYPE");
		equalMap.put("issn", "ISSN");
		equalMap.put("host_title", "HOST_TITLE");
		equalMap.put("year", "YEAR");
		equalMap.put("volume", "VOLUME");
		equalMap.put("issue", "ISSUE");
		equalMap.put("processing_unit", "PROCESSING_UNIT");
		equalMap.put("library_code", "LIBRARY_CODE");
		equalMap.put("holding_number", "HOLDING_NUMBER");

		Map<String, String> listMap = new HashMap<>();
		listMap.put("keyword", "KEYWORD");
		listMap.put("author_name", "AUTHOR");
		listMap.put("affiliation", "AFFILIATION");

		// 常量
		StringBuffer constantValue = new StringBuffer();
		constantValue.append("#DREFIELD IMPORTMAGICEXTENSION=\"idx\"\n");
		constantValue.append("#DREFIELD IMPORTMAGICFRIENDLYTYPE=\"text/plain\"\n");
		constantValue.append("#DREFIELD DOCUMENTCLASS=\"1\"\n");
		constantValue.append("#DREFIELD DOCUMENTTYPE=\"2\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_VERSION=\"0\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_ATTRIBUTES=\"0\"\n");
		constantValue.append("#DRESECTION 0\n");
		constantValue.append("#DREDBNAME HG_HX_NS05\n#DREENDDOC\n\n");
		Writer w = null;
		try {
			FileOutputStream fos = new FileOutputStream(desFile);
			w = new OutputStreamWriter(fos, "utf-8");
			XmlEntity xmlEntity = XmlUtil.parseResponseString(new String(FileUtils.readFileToByteArray(doc)));
			// XmlEntity entity =
			// xmlEntity.findFirstEnclosedOccurrence("paper");
			XmlEntity entity = xmlEntity.findFirstEnclosedOccurrence("record");
			StringBuffer rowValue = new StringBuffer();
			XmlEntity childEntity = null;
			while (null != entity) {
				childEntity = entity.child();
				rowValue = new StringBuffer();
				getNormalMapValue(normalMap, rowValue, childEntity);
				getEqualMapValue(equalMap, rowValue, childEntity);
				getListMapValue(listMap, rowValue, childEntity);
				getTimeValue(DREDATE, rowValue, childEntity);
				getContentValue(DRECONTENT, rowValue, childEntity);
				w.write(additionalReference(PRE_REFERENCE, REFERENCE, childEntity));
				w.write(rowValue.toString());
				w.write(constantValue.toString());
				entity = entity.next();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 日文期刊
	public static void j04_ns06(File doc, File desFile) {

		final String REFERENCE = "doc_id";
		final String PRE_REFERENCE = "ns06";
		final String DRECONTENT = "abstract";
		final String DREDATE = "create_time";
		Map<String, String> normalMap = new HashMap<>();
		normalMap.put("title", "DRETITLE");

		Map<String, String> equalMap = new HashMap<>();
		equalMap.put("doc_id", "DOC_ID");
		equalMap.put("paper_id", "PAPER_ID");
		equalMap.put("classification", "CLASSIFICATION");
		equalMap.put("language", "LANGUAGE");
		equalMap.put("other_language", "OTHER_LANGUAGE");
		equalMap.put("start_page", "START_PAGE");
		equalMap.put("end_page", "END_PAGE");
		equalMap.put("total_page_number", "TOTAL_PAGE_NUMBER");
		equalMap.put("total_reference_number", "TOTAL_REFERENCE_NUMBER");
		equalMap.put("type", "TYPE");
		equalMap.put("paper_type", "PAPER_TYPE");
		equalMap.put("processing_mode", "PROCESSING_MODE");
		equalMap.put("issn", "ISSN");
		equalMap.put("host_title", "HOST_TITLE");
		equalMap.put("host_language", "HOST_LANGUAGE");
		equalMap.put("host_classification", "HOST_CLASSIFICATION");
		equalMap.put("publisher", "PUBLISHER");
		equalMap.put("year", "YEAR");
		equalMap.put("volume", "VOLUME");
		equalMap.put("issue", "ISSUE");
		equalMap.put("processing_unit", "PROCESSING_UNIT");
		equalMap.put("library_code", "LIBRARY_CODE");
		equalMap.put("holding_number", "HOLDING_NUMBER");

		Map<String, String> listMap = new HashMap<>();
		listMap.put("keyword", "KEYWORD");
		listMap.put("author_name", "AUTHOR");
		listMap.put("affiliation", "AFFILIATION");

		// 常量
		StringBuffer constantValue = new StringBuffer();
		constantValue.append("#DREFIELD IMPORTMAGICEXTENSION=\"idx\"\n");
		constantValue.append("#DREFIELD IMPORTMAGICFRIENDLYTYPE=\"text/plain\"\n");
		constantValue.append("#DREFIELD DOCUMENTCLASS=\"1\"\n");
		constantValue.append("#DREFIELD DOCUMENTTYPE=\"2\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_VERSION=\"0\"\n");
		constantValue.append("#DREFIELD KV_DOCINFO_ATTRIBUTES=\"0\"\n");
		constantValue.append("#DRESECTION 0\n");
		constantValue.append("#DREDBNAME HG_HX_NS06\n#DREENDDOC\n\n");
		Writer w = null;
		try {
			FileOutputStream fos = new FileOutputStream(desFile);
			w = new OutputStreamWriter(fos, "utf-8");
			XmlEntity xmlEntity = XmlUtil.parseResponseString(new String(FileUtils.readFileToByteArray(doc)));
			// XmlEntity entity = xmlEntity.findFirstEnclosedOccurrence("paper");
			XmlEntity entity = xmlEntity.findFirstEnclosedOccurrence("record");
			StringBuffer rowValue = new StringBuffer();
			XmlEntity childEntity = null;
			while (null != entity) {
				childEntity = entity.child();
				rowValue = new StringBuffer();
				getNormalMapValue(normalMap, rowValue, childEntity);
				getEqualMapValue(equalMap, rowValue, childEntity);
				getListMapValue(listMap, rowValue, childEntity);
				getTimeValue(DREDATE, rowValue, childEntity);
				getContentValue(DRECONTENT, rowValue, childEntity);
				w.write(additionalReference(PRE_REFERENCE, REFERENCE, childEntity));
				w.write(rowValue.toString());
				w.write(constantValue.toString());
				entity = entity.next();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 英文科技报告
	public static void r02_ns07(File doc, File desFile) {

			final String REFERENCE = "doc_id";
			final String PRE_REFERENCE = "ns07";
			final String DRECONTENT = "abstract";
			final String DREDATE = "create_time";
			Map<String, String> normalMap = new HashMap<>();
			normalMap.put("title", "DRETITLE");

			Map<String, String> equalMap = new HashMap<>();
			equalMap.put("doc_id", "DOC_ID");
			equalMap.put("paper_id", "PAPER_ID");
			equalMap.put("classification", "CLASSIFICATION");
			equalMap.put("language", "LANGUAGE");
			equalMap.put("total_page_number", "TOTAL_PAGE_NUMBER");
			equalMap.put("type", "TYPE");
			equalMap.put("paper_type", "PAPER_TYPE");
			equalMap.put("processing_mode", "PROCESSING_MODE");
			equalMap.put("record_level", "RECORD_LEVEL");
			equalMap.put("year", "YEAR");
			equalMap.put("processing_unit", "PROCESSING_UNIT");
			equalMap.put("library_code", "LIBRARY_CODE");
			equalMap.put("holding_number", "HOLDING_NUMBER");

			Map<String, String> listMap = new HashMap<>();
			listMap.put("keyword", "KEYWORD");
			listMap.put("author_name", "AUTHOR");
			listMap.put("affiliation", "AFFILIATION");

			// 常量
			StringBuffer constantValue = new StringBuffer();
			constantValue.append("#DREFIELD IMPORTMAGICEXTENSION=\"idx\"\n");
			constantValue.append("#DREFIELD IMPORTMAGICFRIENDLYTYPE=\"text/plain\"\n");
			constantValue.append("#DREFIELD DOCUMENTCLASS=\"1\"\n");
			constantValue.append("#DREFIELD DOCUMENTTYPE=\"2\"\n");
			constantValue.append("#DREFIELD KV_DOCINFO_VERSION=\"0\"\n");
			constantValue.append("#DREFIELD KV_DOCINFO_ATTRIBUTES=\"0\"\n");
			constantValue.append("#DRESECTION 0\n");
			constantValue.append("#DREDBNAME HG_HX_NS07\n#DREENDDOC\n\n");
			Writer w = null;
			try {
				FileOutputStream fos = new FileOutputStream(desFile);
				w = new OutputStreamWriter(fos, "utf-8");
				XmlEntity xmlEntity = XmlUtil.parseResponseString(new String(FileUtils.readFileToByteArray(doc)));
				XmlEntity entity = xmlEntity.findFirstEnclosedOccurrence("paper");
				//XmlEntity entity = xmlEntity.findFirstEnclosedOccurrence("record");
				StringBuffer rowValue = new StringBuffer();
				XmlEntity childEntity = null;
				while (null != entity) {
					childEntity = entity.child();
					rowValue = new StringBuffer();
					getNormalMapValue(normalMap, rowValue, childEntity);
					getEqualMapValue(equalMap, rowValue, childEntity);
					getListMapValue(listMap, rowValue, childEntity);
					getTimeValue(DREDATE, rowValue, childEntity);
					getContentValue(DRECONTENT, rowValue, childEntity);
					w.write(additionalReference(PRE_REFERENCE, REFERENCE, childEntity));
					w.write(rowValue.toString());
					w.write(constantValue.toString());
					entity = entity.next();
				}

			} catch (IOException e) {
				System.out.println(e.getMessage());
			} catch (SAXException e) {
				System.out.println(e.getMessage());
			} catch (ParserConfigurationException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					w.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
		
	// 中文期刊
	public static void z01_ns03(File doc, File desFile) {

				final String REFERENCE = "doc_id";
				final String PRE_REFERENCE = "ns03";
				final String DRECONTENT = "abstract";
				final String DREDATE = "create_time";
				Map<String, String> normalMap = new HashMap<>();
				normalMap.put("title", "DRETITLE");

				Map<String, String> equalMap = new HashMap<>();
				equalMap.put("doc_id", "DOC_ID");
				equalMap.put("paper_id", "PAPER_ID");
				equalMap.put("classification", "CLASSIFICATION");
				equalMap.put("language", "LANGUAGE");
				equalMap.put("start_page", "START_PAGE");
				equalMap.put("end_page", "END_PAGE");
				equalMap.put("total_page_number", "TOTAL_PAGE_NUMBER");
				equalMap.put("type", "TYPE");
				equalMap.put("paper_type", "PAPER_TYPE");
				equalMap.put("catalog_code", "CATALOG_CODE");
				equalMap.put("issn", "ISSN");
				equalMap.put("cn", "CN");
				equalMap.put("host_title_alternative", "HOST_TITLE_ALTERNATIVE");
				equalMap.put("publishing_place", "PUBLISHING_PLACE");
				equalMap.put("publisher", "PUBLISHER");
				equalMap.put("host_title", "HOST_TITLE");
				equalMap.put("host_language", "HOST_LANGUAGE");
				equalMap.put("issue", "ISSUE");
				equalMap.put("volume", "VOLUME");
				equalMap.put("record_level", "RECORD_LEVEL");
				equalMap.put("year", "YEAR");
				equalMap.put("processing_unit", "PROCESSING_UNIT");
				equalMap.put("library_code", "LIBRARY_CODE");
				equalMap.put("holding_number", "HOLDING_NUMBER");

				Map<String, String> listMap = new HashMap<>();
				listMap.put("keyword", "KEYWORD");
				listMap.put("author_name", "AUTHOR");
				listMap.put("affiliation", "AFFILIATION");

				// 常量
				StringBuffer constantValue = new StringBuffer();
				constantValue.append("#DREFIELD IMPORTMAGICEXTENSION=\"idx\"\n");
				constantValue.append("#DREFIELD IMPORTMAGICFRIENDLYTYPE=\"text/plain\"\n");
				constantValue.append("#DREFIELD DOCUMENTCLASS=\"1\"\n");
				constantValue.append("#DREFIELD DOCUMENTTYPE=\"2\"\n");
				constantValue.append("#DREFIELD KV_DOCINFO_VERSION=\"0\"\n");
				constantValue.append("#DREFIELD KV_DOCINFO_ATTRIBUTES=\"0\"\n");
				constantValue.append("#DRESECTION 0\n");
				constantValue.append("#DREDBNAME HG_HX_NS03\n#DREENDDOC\n\n");
				Writer w = null;
				try {
					FileOutputStream fos = new FileOutputStream(desFile);
					w = new OutputStreamWriter(fos, "utf-8");
					XmlEntity xmlEntity = XmlUtil.parseResponseString(new String(FileUtils.readFileToByteArray(doc)));
					XmlEntity entity = xmlEntity.findFirstEnclosedOccurrence("paper");
					//XmlEntity entity = xmlEntity.findFirstEnclosedOccurrence("record");
					StringBuffer rowValue = new StringBuffer();
					XmlEntity childEntity = null;
					while (null != entity) {
						childEntity = entity.child();
						rowValue = new StringBuffer();
						getNormalMapValue(normalMap, rowValue, childEntity);
						getEqualMapValue(equalMap, rowValue, childEntity);
						getListMapValue(listMap, rowValue, childEntity);
						getTimeValue(DREDATE, rowValue, childEntity);
						getContentValue(DRECONTENT, rowValue, childEntity);
						w.write(additionalReference(PRE_REFERENCE, REFERENCE, childEntity));
						w.write(rowValue.toString());
						w.write(constantValue.toString());
						entity = entity.next();
					}

				} catch (IOException e) {
					System.out.println(e.getMessage());
				} catch (SAXException e) {
					System.out.println(e.getMessage());
				} catch (ParserConfigurationException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						w.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
	}
		
	private static void getEqualString(StringBuffer rowValue, String key, String value) {
		rowValue.append("#").append("DREFIELD ").append(key).append("=\"").append(value).append("\"").append("\n");
	}

	private static void getNormalString(StringBuffer rowValue, String key, String value) {
		rowValue.append("#").append(key).append(" ").append(value).append("\n");
	}

	private static void getContentString(StringBuffer rowValue, String key, String value) {
		rowValue.append("#").append(key).append("\n").append(value).append("\n");
	}

	private static void getNormalMapValue(Map<String, String> normalMap, StringBuffer rowValue, XmlEntity entity) {
		String value = "";
		for (String key : normalMap.keySet()) {
			value = entity.findFirstOccurrence(key).getValue();
			getNormalString(rowValue, normalMap.get(key), value);
		}
	}

	private static void getEqualMapValue(Map<String, String> equalMap, StringBuffer rowValue, XmlEntity entity) {
		String value = "";
		for (String key : equalMap.keySet()) {
			if (null != entity.findFirstOccurrence(key)) {
				if ("issue".equals(key)) {
					if (null != entity.findFirstOccurrence(key).child().findFirstOccurrence(key))
						value = entity.findFirstOccurrence(key).child().findFirstOccurrence(key).getValue();
					else
						value = "";
				} else
					value = entity.findFirstOccurrence(key).getValue();
				getEqualString(rowValue, equalMap.get(key), value);
			}
		}
	}

	private static void getContentValue(String drecontent, StringBuffer rowValue, XmlEntity entity) {
		String value = entity.findFirstOccurrence(drecontent).getValue();
		getContentString(rowValue, "DRECONTENT", value);
	}

	private static void getListMapValue(Map<String, String> listMap, StringBuffer rowValue, XmlEntity entity) {
		StringBuffer sbValue = new StringBuffer();
		XmlEntity listEntity = null;
		for (String key : listMap.keySet()) {
			sbValue = new StringBuffer();
			if ("author_name".equals(key) || "affiliation".equals(key)) {
				listEntity = entity.findFirstOccurrence("author");
				while (null != listEntity) {
					if (null != listEntity.findFirstOccurrence(key)) {
						sbValue = sbValue.append(listEntity.findFirstOccurrence(key).getValue()).append(SEMICOLON);
					}
					listEntity = listEntity.next();
				}
			} else if ("supervisor_name".equals(key) || "supervisor_affiliation".equals(key)) {
				listEntity = entity.findFirstOccurrence("contributor");
				while (null != listEntity) {
					if ("supervisor_name".equals(key)) {
						if (null != listEntity.findFirstOccurrence("supervisor_name")) {
							sbValue = sbValue.append(listEntity.findFirstOccurrence("supervisor_name").getValue())
									.append(SEMICOLON);
						} else if (null != listEntity.findFirstOccurrence("supervisor")) {
							sbValue = sbValue.append(listEntity.findFirstOccurrence("supervisor").getValue())
									.append(SEMICOLON);
						}
					} else {
						if (null != listEntity.findFirstOccurrence(key)) {
							sbValue = sbValue.append(listEntity.findFirstOccurrence(key).getValue()).append(SEMICOLON);
						}
					}
					listEntity = listEntity.next();
				}
			} else {
				listEntity = entity.findFirstOccurrence(key);
				while (null != listEntity) {
					sbValue = sbValue.append(listEntity.getValue()).append(SEMICOLON);
					listEntity = listEntity.next().findFirstOccurrence(key);
				}

			}
			if (sbValue.toString().endsWith(SEMICOLON))
				sbValue = new StringBuffer(sbValue.substring(0, sbValue.length() - 1));
			getEqualString(rowValue, listMap.get(key), sbValue.toString());

		}
	}

	private static void getTimeValue(String dredate, StringBuffer rowValue, XmlEntity entity) {
		String value = entity.findFirstOccurrence(dredate).getValue();
		if (StringUtils.isNotBlank(value) && value.length() > 10) {
			value = value.substring(0, 10);
			String time = DateUtil.toTime(value, DateUtil.datePattern);
			getNormalString(rowValue, "DREDATE", time);
			getEqualString(rowValue, "INIT_TIME", time);
			getEqualString(rowValue, "DAYTIMESTR", value);
			getEqualString(rowValue, "MONTHTIMESTR",
					DateUtil.strToDateFormat(value, DateUtil.datePattern, DateUtil.monthPattern));
			getEqualString(rowValue, "YEARTIMESTR",
					DateUtil.strToDateFormat(value, DateUtil.datePattern, DateUtil.yearPattern));
		}
	}

	private static String additionalReference(String pre_reference, String reference, XmlEntity entity) {
		String value = "";
		StringBuffer refValue = new StringBuffer();
		if (null != entity.findFirstOccurrence(reference))
			value = entity.findFirstOccurrence(reference).getValue();
		else
			value = UUIDUtil.getUUID().substring(0, 6);
		refValue.append("#DREREFERENCE ").append(pre_reference).append("-").append(value).append("\n");
		return refValue.toString();
	}
}
