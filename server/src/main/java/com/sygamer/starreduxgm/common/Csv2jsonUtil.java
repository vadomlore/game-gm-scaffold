package com.sygamer.starreduxgm.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LF on 2017/12/12 0012.
 */
public class Csv2jsonUtil {
    private String fileName = null;
    private BufferedReader br = null;
    private List<String> list = new ArrayList<String>();

    public Csv2jsonUtil() {

    }

    public Csv2jsonUtil(String fileName) throws Exception {
        this.fileName = fileName;
        br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"GBK"));
        String stemp;
        while ((stemp = br.readLine()) != null) {
            list.add(stemp);
        }
    }

    public Csv2jsonUtil(InputStream inputStream) throws Exception {
        br = new BufferedReader(new InputStreamReader(inputStream,"GBK"));
        String stemp;
        while ((stemp = br.readLine()) != null) {
            list.add(stemp);
        }
    }

    public List getList(){
        return list;
    }

    /**
     * 获取行数
     * @return
     */
    public int getRowNum(){
        return list.size();
    }

    /**
     * 获取列数
     * @return
     */
    public int getColNum(){
        if (!list.toString().equals("[]")) {
            if (list.get(0).toString().contains(",")) {
                return list.get(0).toString().split(",").length;
            } else if (list.get(0).toString().trim().length() != 0) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
    /**
     * 获取指定行
     * @param index
     * @return
     */
    public String getRow(int index) {
        if (this.list.size() != 0) {
            return (String) list.get(index);
        } else {
            return null;
        }
    }

    /**
     * 获取指定列
     * @param index
     * @return
     */
    public String getCol(int index) {
        if (this.getColNum() == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        String tmp = null;
        int colnum = this.getColNum();
        if (colnum > 1) {
            for (Iterator it = list.iterator(); it.hasNext();) {
                tmp = it.next().toString();
                sb = sb.append(tmp.split(",")[index] + ",");
            }
        } else {
            for (Iterator it = list.iterator(); it.hasNext();) {
                tmp = it.next().toString();
                sb = sb.append(tmp + ",");
            }
        }
        String str = new String(sb.toString());
        str = str.substring(0, str.length() - 1);
        return str;
    }
    /**
     * 获取某个单元格
     * @param row
     * @param col
     * @return
     */
    public String getString(int row, int col) {
        String temp = null;
        int colnum = this.getColNum();
        if (colnum > 1) {
            temp = list.get(row).toString().split(",")[col];
        } else if(colnum == 1){
            temp = list.get(row).toString();
        } else {
            temp = null;
        }
        return temp;
    }

    public void CsvClose()throws Exception{
        this.br.close();
    }
    /**
     *去表头
     **/
    public String removehead(String str){
        String[] str_1=str.split(",");
        String sb=new String();
        for(int i=1;i<str_1.length;i++){
            sb=sb+str_1[i]+",";
        }
        return sb;
    }

    public JSONArray csv2json(int[] arr){
        JSONArray array = new JSONArray();
        int row = this.getRowNum();
        int col = this.getColNum();
        int len = arr.length;
        for (int i = 0;i<len;i++) {
            JSONObject jsonObject = new JSONObject();
            String value = this.getCol(arr[i]);
            jsonObject.put(this.getString(0, arr[i]),this.removehead(value));
            array.add(jsonObject);
        }
        /*for (int i = 0;i<col;i++) {
            JSONObject jsonObject = new JSONObject();
            String value = this.getCol(i);
            jsonObject.put(this.getString(0, i),this.removehead(value));
            array.add(jsonObject);
        }*/
        return array;
    }

}
