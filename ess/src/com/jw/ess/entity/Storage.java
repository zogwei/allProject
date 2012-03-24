package com.jw.ess.entity;

/**
 * 库存实体类
 * 
 * @author j&w
 * 
 */
public class Storage
{
	private int id;

	private float area;// 总面积

	private float count;// 总金额
	
	private int tenantId;//租户id
	
	private Floor floor;//查询库存时用于保存该地板的部分信息
	
	private Spec spec;//查询库存时用于保存该规格的部分信息
	
	private FloorCategory floorCategory;//查询库存时用于保存该地板类别的部分信息
	
	private Vein vein;//查询库存时用于保存该纹理的部分信息

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public float getArea()
	{
		return area;
	}

	public void setArea(float area)
	{
		this.area = area;
	}

	public float getCount()
	{
		return count;
	}

	public void setCount(float count)
	{
		this.count = count;
	}
	
	public Floor getFloor()
    {
        return floor;
    }
	
	public void setFloor(Floor floor)
    {
        this.floor = floor;
    }
	
	public Spec getSpec() {
		return spec;
	}

	public void setSpec(Spec spec) {
		this.spec = spec;
	}

	public FloorCategory getFloorCategory() {
		return floorCategory;
	}

	public void setFloorCategory(FloorCategory floorCategory) {
		this.floorCategory = floorCategory;
	}

	public Vein getVein() {
		return vein;
	}

	public void setVein(Vein vein) {
		this.vein = vein;
	}

}
