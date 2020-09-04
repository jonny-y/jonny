package com.jonny.mybatisplus.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jonny.mybatisplus.mybatisplus.dao.UserMapper;
import com.jonny.mybatisplus.mybatisplus.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void select(){
        List<User> list = userMapper.selectList(null);
        Assert.assertEquals(5,list.size());
        list.forEach(System.out::println);
    }

    @Test
    public void insert(){
        User user = new User();
        user.setAge(19);
        user.setCreate_time(LocalDateTime.now());
        user.setManager_id(1088248166370832385L);
        user.setName("张达明");

        int insert = userMapper.insert(user);
        System.out.println("受影响行数："+insert);
    }

    @Test
    public void selectbyid(){
        User user = userMapper.selectById(1087982257332887553L);
        System.out.println("user:"+user);
    }

    @Test
    public void selectbyids(){
        List<Long> ids = Arrays.asList(1087982257332887553L, 1088250446457389058L);
        List<User> users = userMapper.selectBatchIds(ids);
        users.forEach(System.out::println);
    }

    @Test
    public void selectbyMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("age",25);         //根据条件查询，key为表中字段名
        map.put("name","王天风");
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    /**
     * 使用条件构造器查询
     */
    @Test
    public void selectWrapper(){
        //方式一
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //方式二
        //QueryWrapper<User> query = Wrappers.<User>query();
        /**
         * 1、名字中包含雨并且年龄小于40
         * 	name like '%雨%' and age<40
         */
        queryWrapper.like("name", "雨").lt("age", 40);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 2、名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
     *    name like '%雨%' and age between 20 and 40 and email is not null
     */
    @Test
    public void selectWrapper2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").between("age",20,40).isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }

    /**
     * 3、名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     * name like '王%' or age>=25 order by age desc,id asc
     */
    @Test
    public void selectWrapper3(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王").or().ge("age", 25).orderByDesc("age").orderByAsc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }

    /**
     * apply("date_format(dateColumn,'%Y-%m-%d') = {0}", "2008-08-08")
     *4、创建日期为2019年2月14日并且直属上级为名字为王姓
     *       date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')
     */
    @Test
    public void selectWrapper4(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}","2019-02-14").inSql("manager_id","select id from user where name like '王%'");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }
    /**
     * 5、名字为王姓并且（年龄小于40或邮箱不为空）
     *     name like '王%' and (age<40 or email is not null)
     */
    @Test
    public void selectWrapper5(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","王").and(wq123->wq123.lt("age",40).or().isNotNull("email"));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 6、名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     *     name like '王%' or (age<40 and age>20 and email is not null)
     */
    @Test
    public void selectWrapper6(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","王").or(abc->abc.lt("age",40).gt("age",20).isNotNull("email"));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }
    /**
     * 7、（年龄小于40或邮箱不为空）并且名字为王姓
     *     (age<40 or email is not null) and name like '王%'
     */
    @Test
    public void selectWrapper7(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.nested(abc -> abc.lt("age", 40).or()
                .isNotNull("email")).likeRight("name", "王");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    /**
     * 8、年龄为30、31、34、35
     *     age in (30、31、34、35)
     */
    @Test
    public void selectWrapper8() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age",Arrays.asList(30,31,34,35));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    /**
     * 9)	只返回满足条件的其中一条语句即可
     * limit 1
     */
    //注：这种方法慎用，有SQL注入风险，确保没有风险的情况下使用
    @Test
    public void selectWrapper9() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age",Arrays.asList(30,31,34,35)).last("limit 1");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 查询结果只显示指定列的结果集
     */
    @Test
    public void selectSupper(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //方式一，只显示指定的
        queryWrapper.select("name","age").like("name", "雨").lt("age", 40);
        //方式二，排除列
//        queryWrapper.select(User.class,info->!info.getColumn().equals("create_time")&&!info.getColumn().equals("manager_id")).like("name","雨").lt("age",40);

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * condition 使用，如条件搜索
     */
    @Test
    public void selectWrapper10() {
        String name="";
        String email="l";
        condition(name,email);
    }
    public void condition(String name,String email){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //普通方式
//        if(StringUtils.isNotEmpty("name")){
//            queryWrapper.like("name",name);
//        }
//        if(StringUtils.isNotEmpty("email")){
//            queryWrapper.like("email",email);
//        }
        //condition方式
        queryWrapper.like(StringUtils.isNotBlank(name),"name",name)
                .like(StringUtils.isNotBlank(email),"email",email);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }
    /**
     * 根据实体作为参数
     */
    @Test
    public void selectWrapperEntity() {
        User user = new User();
        user.setAge(19);
        user.setManager_id(1088248166370832385L);
        user.setName("张达明");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);//默认是使用等号查询关系，也可在实体属性上通过注解的方式改为其他符号（列@TableField(condition = SqlCondition.LIKE)若没有想要的也可自定义）
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);


    }

    /**
     * AllEq
     */
    @Test
    public void selectWrapperAllEq() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","张雨琪");
        map.put("age",25);
        //queryWrapper.allEq(map,false);//true：某个值null,则生成is null的语句，false:某个值为null，则出现在sql语句中
        queryWrapper.allEq((k,v)->!k.equals("name"),map);//过滤掉name字段的条件（也可以过滤value，但是要考虑类型问题）
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * ⦁	统计查询：
     * 9)	按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。
     * 并且只取年龄总和小于500的组。
     * select avg(age) avg_age,min(age) min_age,max(age) max_age
     * from user
     * group by manager_id
     * having sum(age) <500
     */
    @Test
    public void selectWrapperMaps() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //selectMaps一般有两种用法，一是，只返回指定的列，可以避免返回其他不需要的null值
//        queryWrapper.select("name","age").like("name","雨").lt("age",40);
        //二是，统计查询的场景
        queryWrapper.select("avg(age) avg_age","min(age) min_age","max(age) max_age")
                .groupBy("manager_id").having("sum(age)<{0}",500);
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    /**
     * 当直返会一列是使用
     */
    @Test
    public void selectWrapperObjs() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //运行结果可以看到，孙然指定返回两列，但结果只返回一列
        queryWrapper.select("name","age").like("name","雨").lt("age",40);
        List<Object> objects = userMapper.selectObjs(queryWrapper);
        objects.forEach(System.out::println);

    }
    /**
     * selectCount用于查询总记录数
     */
    @Test
    public void selectWrapperCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //注意：不能指定返回列
        queryWrapper.like("name", "雨").lt("age", 40);
        Integer integer = userMapper.selectCount(queryWrapper);
        System.out.println("总记录数："+integer);
    }

    /**
     * selectOne  用于查询只有一条结果的，
     * 注意：返回结果只能有一条或null
     */
    @Test
    public void selectWrapperOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //注意：返回结果只能有一条或null
        queryWrapper.like("name", "刘红雨").lt("age", 40);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println("user："+user);
    }

    /**
     * Lambda条件构造器
     */
    @Test
    public void selecLambda() {
        //Lambda条件构造器有三种方式
        //方式一
//        LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
        //方式二
//        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //方式三
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        wrapper.like(User::getName,"雨").lt(User::getAge,40);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * （年龄小于40或邮箱不为空）并且名字为王姓
     *     (age<40 or email is not null) and name like '王%'
     */
    @Test
    public void selecLambda2() {
        //Lambda条件构造器有三种方式
        //方式一
//        LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
        //方式二
//        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //方式三
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        wrapper.nested(aa->aa.lt(User::getAge,40).or().isNotNull(User::getEmail)).likeRight(User::getName,"王");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    /**
     * 3.0.7新增的条件构造器
     */
    @Test
    public void selecLambda3() {
        List<User> users = new LambdaQueryChainWrapper<User>(userMapper)
                .likeRight(User::getName, "王").lt(User::getAge, 40)
                .list();
        users.forEach(System.out::println);
    }

    /**
     * 自定义查询
     * 3.0.7以上支持
     */
    @Test
    public void selecMy() {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        //注意：返回结果只能有一条或null
//        queryWrapper.like("name", "刘红雨").lt("age", 40);

        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        wrapper.nested(aa->aa.lt(User::getAge,40).or().isNotNull(User::getEmail)).likeRight(User::getName,"王");
        List<User> users = userMapper.selectAll(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 分页查询
     */
    @Test
    public void selectPage(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.ge("age",26);
        Page<User> userPage = new Page<>(1,2,true);
        IPage<User> iPage = userMapper.selectPage(userPage, userQueryWrapper);
        System.out.println("总页数："+iPage.getPages());
        System.out.println("总记录数："+iPage.getTotal());

    }


}

