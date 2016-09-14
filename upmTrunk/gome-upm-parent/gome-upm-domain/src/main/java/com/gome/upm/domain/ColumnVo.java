package com.gome.upm.domain;

/**
 * 柱形图实体
 * @author liuyuqiang
 *
 */
public class ColumnVo {
	private String color;
	private Long y;
	public ColumnVo(){
		
	}
	public ColumnVo(String color,Long y){
		this.color=color;
		this.y=y;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Long getY() {
		return y;
	}
	public void setY(Long y) {
		this.y = y;
	}
}
