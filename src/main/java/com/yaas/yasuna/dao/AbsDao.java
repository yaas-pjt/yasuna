package com.yaas.yasuna.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;


public abstract class AbsDao<C> {

	protected ResultSetHandler<?> rsh;
	protected QueryRunner queryRunner;
	protected RowProcessor row;



	protected void prepare(Map<String, String> cp, Object object) {
		row = mapping(cp, object);
		queryRunner = new QueryRunner();
	}

	protected RowProcessor mapping(Map<String, String> cp, Object object) {

		BeanProcessor bp = new BeanProcessor(cp);
		RowProcessor rp = new BasicRowProcessor(bp);
		return rp;
	}

	protected List<C> getAll(C c,Map<String, String> cp, Connection connection, String sql){

		prepare(cp, c);

		try {
			rsh = new BeanListHandler<Object>(c.getClass(), row);

			List<C> list = (List<C>) queryRunner.query(connection,sql,rsh);

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}



	protected C getById(C c, Map<String, String> cp, Connection connection, String sql, String id) {

		prepare(cp, c.getClass());
		//QueryRunner queryRunner = new QueryRunner();

		try {
			ResultSetHandler helper = new BeanHandler(c.getClass(), row);
			C entity  = (C) queryRunner.query(connection,sql,helper,id);

			return entity;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new NullPointerException();
		}

	}



	protected List<C> getByConditions(C c, Map<String, String> cp, Connection connection, String sql, List<Object>args){

		prepare(cp, c.getClass());

		try {
			rsh = new BeanListHandler<Object>(c.getClass(), row);

			List<C> list = (List<C>) queryRunner.query(connection,sql,rsh, args.toArray());

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}



	protected int add(C c,Connection connection, String sql, List<Object> args) {

		int row = 0;


		 try {
			row = queryRunner.update(connection, sql, args.toArray());
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}



	protected int update(Connection connection, String sql, List<Object> args) {

		int row = 0;


		 try {
			row = queryRunner.update(connection, sql, args.toArray());
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}finally {
			try {
				DbUtils.commitAndClose(connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}



	protected int delete(C c,Connection connection, String sql, List<Object> args) {

		int row = 0;


		 try {
			row = queryRunner.update(connection, sql, args.toArray());
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}

}
