package com.gangtok.nielit.skdirectory.model;

/**
 * Created by NgocTri on 11/7/2015.
 */
public class Employee {

    private int id;
    private String emp_Name,designation_Name,place_of_posting,email_id,office_address;
    private String office_landline,office_mobile,home_landline,home_mobile,fax;
    private int designation_ID;
    private byte[] emp_Image;
    private int flag;

    private String department_Name,postingName;
    public Employee(int id, String emp_Name,String designation_Name) {

        this.id = id;
        this.emp_Name = emp_Name;
        this.designation_Name=designation_Name;

    }
    public Employee(int id, String emp_Name,String designation_Name,int designation_ID ,String department_Name,String postingName ) {

        this.id = id;
        this.emp_Name = emp_Name;
        this.designation_Name=designation_Name;
        this.designation_ID=designation_ID;
        this.department_Name=department_Name;
        this.postingName=postingName;
    }
    public Employee(int id, String emp_Name,String designation_Name,int designation_ID,String place_of_posting,
                    String office_landline,String office_mobile,String home_landline,
                    String home_mobile,String fax,String email_id,
                    String office_address,byte[] emp_Image,int flag) {

        this.id = id;
        this.emp_Name = emp_Name;
        this.designation_Name=designation_Name;
        this.place_of_posting=place_of_posting;
        this.email_id=email_id;
        this.office_address=office_address;
        this.office_landline=office_landline;
        this.office_mobile=office_mobile;
        this.home_landline=home_landline;
        this.home_mobile=home_mobile;
        this.fax=fax;
        this.designation_ID=designation_ID;
        this.emp_Image=emp_Image;
        this.flag=flag;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getOffice_landline() {
        return office_landline;
    }

    public void setOffice_landline(String office_landline) {
        this.office_landline = office_landline;
    }
    public String getOffice_mobile() {
        return office_mobile;
    }

    public void setOffice_mobile(String office_mobile) {
        this.office_mobile = office_mobile;
    }
    public String getHome_landline() {
        return home_landline;
    }

    public void setHome_landline(String home_landline) {
        this.home_landline = home_landline;
    }
    public String getHome_mobile() {
        return home_mobile;
    }

    public void setHome_mobile(String home_mobile) {
        this.home_mobile = home_mobile;
    }
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    public int getDesignationId() {
        return designation_ID;
    }

    public void setDesignationId(int designation_ID) {
        this.designation_ID = designation_ID;
    }
    public String getEmpName() {
        return emp_Name;
    }

    public void setEmpName(String emp_Name) {
        this.emp_Name = emp_Name;
    }

    public String getDesignation_Name() {
        return designation_Name;
    }

    public void setDesignation_Name(String designation_Name) {
        this.designation_Name = designation_Name;
    }

    public String getplace_of_posting() {
        return place_of_posting;
    }

    public void setplace_of_posting(String place_of_posting) {
        this.place_of_posting = place_of_posting;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getOffice_address() {
        return office_address;
    }

    public void setOffice_address(String office_address) {
        this.office_address = office_address;
    }

    public byte[] getEmp_Image() {
        return emp_Image;
    }

    public void setEmp_Image(byte[] emp_Image) {
        this.emp_Image = emp_Image;
    }


    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


    /*Department Table Value*/

    public String getDepartment_Name() {
        return department_Name;
    }

    public void setDepartment_Name(String department_name) {
        this.department_Name = department_name;
    }

    public String getPostingName() {
        return postingName;
    }

    public void setPostingName(String postingName) {
        this.postingName = postingName;
    }

}
