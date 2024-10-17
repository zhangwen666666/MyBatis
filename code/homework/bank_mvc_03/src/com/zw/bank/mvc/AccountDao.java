package com.zw.bank.mvc;

import com.zw.bank.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    public int insert(Account account, Connection connection) {
        PreparedStatement ps = null;
        int count = 0;
        try {
            String sql = "insert into t_act(actno, balance) values(?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, account.getActno());
            ps.setDouble(2, account.getBalance());
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }


    public int deleteByActno(Long id, Connection connection) {
        PreparedStatement ps = null;
        int count = 0;
        try {
            String sql = "delete from t_act where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }


    public int update(Account account, Connection connection) {
        PreparedStatement ps = null;
        int count = 0;
        try {
            System.out.println(connection);
            String sql = "update t_act set balance=?, actno=? where id=?";
            ps = connection.prepareStatement(sql);
            ps.setDouble(1, account.getBalance());
            ps.setString(2, account.getActno());
            ps.setLong(3, account.getId());
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    public Account selectById(Long id, Connection connection) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Account account = null;
        try {
            String sql = "select actno, balance from t_act where id=?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account(id, rs.getString("actno"), rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return account;
    }


    public Account selectByActno(String actno, Connection connection) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Account account = null;
        try {
            System.out.println(connection);
            String sql = "select id, balance from t_act where actno=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, actno);
            rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account(rs.getLong("id"), actno, rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return account;
    }

    public List<Account> selectAll(Connection connection) {
        List<Account> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select id,actno, balance from t_act";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String actno = rs.getString("actno");
                double balance = rs.getDouble("balance");
                list.add(new Account(id,actno,balance));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return list;
    }
}
