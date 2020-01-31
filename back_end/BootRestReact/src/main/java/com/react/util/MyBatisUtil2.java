package com.react.util;
//������ ��������
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil2 {
	// mybatis ȯ�� ���� ���� ��ġ(���)
    private final static String RESOURCE = "com/react/util/SqlMapConfig.xml";
    private static SqlSessionFactory factory = null;
    
    static { //�� �κ��� üũ�� �ʿ� ���� �ѹ��� �ϵ��� static���� ���ξ���.
    	Reader reader= null;
        
        try {
              // mybatis.xml �ڿ��� ��´�.
              reader = Resources.getResourceAsReader(RESOURCE); //�������Ͽ� �Է� ������ ����
              factory = new SqlSessionFactoryBuilder().build(reader);
              
        } catch (IOException e) {
              e.printStackTrace();
              
        }
    }
    
    // MyBatis SqlSession�� ��� �޼ҵ� //�� ����ƽ �޼��带 ȣ���ؼ� ����.
    public static SqlSession getSqlSession() {
        return factory.openSession();
    }
}
