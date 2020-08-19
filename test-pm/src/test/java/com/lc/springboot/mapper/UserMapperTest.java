package com.lc.springboot.mapper;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.springboot.common.crypto.Sha256;
import com.lc.springboot.user.enums.UserStatus;
import com.lc.springboot.user.mapper.UserMapper;
import com.lc.springboot.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@Slf4j
public class UserMapperTest {

  @Resource UserMapper userMapper;

  private User user;

  @Before
  public void setUp() {
    user =
        User.builder()
            .userName("superMan")
            .userPassword(Sha256.getSHA256Str("111111"))
            .email("ruanpanliang@126.com")
            .phone("12300000000")
            .status(UserStatus.NORMAL.getCode())
            .userType("admin")
            .userAccount("liangc")
            .build();
  }

  @Test
  public void testCreateUser() {
    // create new
    int insert = userMapper.insert(user);
    assertEquals(1, insert);
    // check exists
    User user = userMapper.selectById(this.user.getId());

    assertEquals(user.getUserName(), this.user.getUserName());
    assertEquals(user.getUserPassword(), this.user.getUserPassword());
    assertEquals(user.getEmail(), this.user.getEmail());
    assertEquals(user.getPhone(), this.user.getPhone());
    assertEquals(user.getStatus(), this.user.getStatus());
    assertEquals(user.getUserType(), this.user.getUserType());
    assertEquals(user.getUserAccount(), this.user.getUserAccount());
  }

  @Test
  public void testListUser() {
    IPage page = new Page(1, 3);
    IPage result = userMapper.selectPage(page, null);
    assertTrue(result.getRecords().size() >= 0);

    result.getRecords().forEach(System.out::println);
  }

  @Test
  public void testUpdateUser() {
    // create new
    user.setUserName(System.currentTimeMillis() + "_name");
    user.setUserAccount(String.valueOf(System.currentTimeMillis()));
    int insert = userMapper.insert(user);

    assertEquals(1, insert);

    // update
    User user1 = new User();
    user1.setUserName(user.getUserName() + "-update");
    user1.setUserPassword(Sha256.getSHA256Str("123456"));
    user1.setEmail(RandomUtil.randomString(10) + "@" + RandomUtil.randomNumbers(3) + ".com");
    user1.setPhone("1" + RandomUtil.randomNumbers(10));
    user1.setStatus(UserStatus.LOGOUT.getCode());
    user1.setUserType("normal");
    user1.setUserAccount(RandomUtil.randomString(10));
    user1.setId(user.getId());

    int update = userMapper.updateById(user1);
    assertEquals(1, update);

    User result = userMapper.selectById(this.user.getId());
    assertEquals(user1.getUserName(), result.getUserName());
    assertEquals(user1.getUserPassword(), result.getUserPassword());
    assertEquals(user1.getEmail(), result.getEmail());
    assertEquals(user1.getPhone(), result.getPhone());
    assertEquals(user1.getStatus(), result.getStatus());
    assertEquals(user1.getUserType(), result.getUserType());
    assertEquals(user1.getUserAccount(), result.getUserAccount());

    // update again
    user1.setUserName("Kevin");
    update = userMapper.updateById(user1);
    assertEquals(1, update);
    result = userMapper.selectById(this.user.getId());
    assertEquals(user1.getUserName(), result.getUserName());
  }

  @Test
  public void testDeleteUser() {
    // create new
    user.setUserName(System.currentTimeMillis() + "_name");
    user.setUserAccount(String.valueOf(System.currentTimeMillis()));
    int insert = userMapper.insert(user);

    assertEquals(1, insert);

    int deleteCount = userMapper.deleteById(user.getId());
    assertEquals(1, deleteCount);

    // 查询检验
    IPage page = new Page(1, 3);
    User user = userMapper.selectById(this.user.getId());
    assertTrue(user == null);
  }

  // @After
  // public void destroy() {
  // }
}
