package cn.itcast.a_beans;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.junit.Test;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class App {

    //1.对javabean的基本操作
    @Test
    public void test1() throws Exception{
        //a.基本操作
        Admin admin = new Admin();
//        admin.setUserName("Jack");
//        admin.setPwd("999");

        //b.BeanUtils组件实现对象属性的拷贝
        BeanUtils.copyProperty(admin,"userName","Jack");
        BeanUtils.copyProperty(admin,"age",18);

        // 总结1： 对于基本数据类型，会自动进行类型转换!

        //c.对象的拷贝
        Admin newAdmin = new Admin();
        BeanUtils.copyProperties(newAdmin,admin);

        //d.map数据，拷贝到对象中
        Admin adminMap = new Admin();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userName","Jack");
        map.put("age",29);
        // 注意：map中的key要与javabean的属性名称一致
        BeanUtils.populate(adminMap,map);

        // 测试
        System.out.println(adminMap.getUserName());
        System.out.println(adminMap.getAge());
    }

    //2.自定义日期类型转换器
    @Test
    public void test2() throws Exception{
        //模拟表单数据
        String name = "Jack";
        String age = "20";
        String birth = "2019-08-07";

        //对象
        Admin admin = new Admin();

        //注册日期类型转换器：1，自定义的方式
        ConvertUtils.register(new Converter() {
            // 转换的内部实现方法，需要重写
            @Override
            public Object convert(Class type, Object value) {

                //判断
                if(type != Date.class){
                    return null;
                }
                if(value == null || "".equals(value.toString().trim())){
                    return null;
                }
                try {
                    // 字符串转换为日期
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.parse(value.toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        },Date.class);

        // 把表单提交的数据，封装到对象中
        BeanUtils.copyProperty(admin, "userName", name);
        BeanUtils.copyProperty(admin, "age", age);
        BeanUtils.copyProperty(admin, "birth", birth);

        //------ 测试------
        System.out.println(admin);
    }

    //3. 使用提供的日期类型转换器工具类
    @Test
    public void test3() throws Exception {
        // 模拟表单数据
        String name = "userName";
        String age = "20";
        String birth = null;

        // 对象
        Admin admin = new Admin();

        // 注册日期类型转换器：2， 使用组件提供的转换器工具类
        ConvertUtils.register(new DateLocaleConverter(), Date.class);

        // 把表单提交的数据，封装到对象中
        BeanUtils.copyProperty(admin, "userName", name);
        BeanUtils.copyProperty(admin, "age", age);
        BeanUtils.copyProperty(admin, "birth", birth);

        //------ 测试------
        System.out.println(admin);
    }
}
