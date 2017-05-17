package silverlion.com.house.register.sortlistview;


public class SortModel {
//	地区名称	name
//	区号	code

	private String name;   //地方
	private String sortLetters;  //��ʾ����ƴ��������ĸ
	private String area_code;//区号

	public SortModel(String name,String stand,String code){
		this.name = name;
		this.sortLetters = stand;
		this.area_code = code;
	}
	public SortModel(){
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	public void setArea_code(String area_code){this.area_code = area_code;}
	public String getArea_code(){return area_code;}
}
