package controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TextArea;

public class Compiler {
	/**
	 * 这个类用于将字符串通过相应的规则变换为图形，是一个编译器类
	 * @author Zhanbiao_Zhu
	 */
	private ShapeFactory shapeFactory;
	private DrawController drawController;
	private String code;
	private TextArea textArea;
	public TextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}

	/**
	 * 获得圆角矩形里面的文字
	 * 使用了正则表达式进行匹配
	 * @author Zhanbiao_Zhu
	 * @param shape
	 * @return
	 */
	public Double[] getRound(String shape){
		Double []list=new Double[4];
		for(int i =0;i<list.length;i++)list[i] = new Double("0");
		Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
		Matcher matcher = pattern.matcher(shape);
		if(matcher.find()){
			String temp = matcher.group();
			String tt[]=temp.split(",");
			for(int i =0;i<tt.length;i++){
				list[i] = Double.valueOf(tt[i]);
			}
		}
		return list;
	}
	/**
	 * 获得尖括号里面的内容，也就是id号
	 * @author Zhanbiao_Zhu
	 * @param shape
	 * @return
	 */
	public int  getID(String shape){
		int id=0;
		Pattern pattern = Pattern.compile("(?<=\\<)[^\\>]+");
		Matcher matcher = pattern.matcher(shape);
		if(matcher.find()){
			String temp = matcher.group();
			temp = temp.trim();
			id = Integer.valueOf(temp);
		}
		return id;
	}
	/**
	 * 获得大括号内的的css信息
	 * @author Zhanbiao_Zhu
	 * @param shape
	 * @return
	 */
	public String getCss(String shape){
		String temp=null;
		Pattern pattern = Pattern.compile("(?<=\\{)[^\\}]+");
		Matcher matcher = pattern.matcher(shape);
		if(matcher.find()){
			temp = matcher.group();
		}
		return temp;
	}
	/**
	 *
	 * @param shape
	 * @return
	 */
	public String getText(String shape){
		Pattern pattern = Pattern.compile("(?<=\\[)[^\\]]+");
		Matcher matcher = pattern.matcher(shape);
		String temp = " ";
		if(matcher.find()){
			temp = matcher.group();
			temp=temp.trim();
			return temp+" ";
		}
		System.out.println(temp);
		return temp;
	}
	public String getShapeType(String shape){
		Pattern pattern = Pattern.compile("[^\\<]+");
		Matcher matcher = pattern.matcher(shape);
		String temp = null;
		if(matcher.find()){
			temp = matcher.group();
		}
		return temp;
	}

	public String [] getItem(){
		String []item = code.split(";");
		return item;
	}

	public void compireProduce(String code){
		this.code = code.trim();

		shapeFactory.setCountShapeID(0);
		drawController.reset();
		String items[]=getItem();
		this.code = code.replaceAll("\n|\r", "");
		ArrayList<String>cssList=new ArrayList<>();
		for(int i =0;i<items.length;i++){
			items[i]=items[i].trim();
			if(items[i]==null&&items[i].length()==0)continue;
			Double list[] = getRound(items[i]);
			String text = getText(items[i]);
			int id = getID(items[i]);
			String css =getCss(items[i]);
			String kind = getShapeType(items[i]);
		    if(kind == null) return;
			if(kind == null)return;
			if(kind.indexOf("Line")==-1){
				cssList.add(css);
			}
			shapeFactory.produce(kind,list[0],list[1],list[2],list[3],text,id);
		}
		for(int i=0;i<cssList.size();i++){
			drawController.getList().get(i).setCSS(cssList.get(i));
		}
		drawController.updateCodeArea();
	}

	public ShapeFactory getShapeFactory() {
		return shapeFactory;
	}
	public void setShapeFactory(ShapeFactory shapeFactory) {
		this.shapeFactory = shapeFactory;
		this.drawController=shapeFactory.getDrawController();
	}
}
