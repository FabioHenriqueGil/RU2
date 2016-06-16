/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufpr.br.dao;

import br.ufpr.jdbc.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import ufpr.br.modelo.Credito;

/**
 *
 * @author fabio
 */
public class CreditoDao implements IDao<Credito> {

    private Connection connection;

    public CreditoDao() {
        this.connection = new ConnectionFactory().getConnection();

    }

    @Override
    public void inserir(Credito obj) {

        String sql = "insert into Credito (saldo) values(?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, obj.getSaldo());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            int idCredito = 0;
            if (rs.next()) {
                idCredito = rs.getInt(1);

            }
            obj.setId(idCredito);
            stmt.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("erro no adiciona credito");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void alterar(Credito obj) {

        String sql = "update Credito set saldo=? where id=" + obj.getId();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, obj.getSaldo());
            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("erro no altera credito");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deletar(Credito obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Credito buscar(int id) {

        Credito cre = new Credito();
        String sql = "select * from Credito where id =" + id;

        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            // executa
            ResultSet resultado = stmt.executeQuery();
            // alimenta a lista
            while (resultado.next()) {
                cre.setId(resultado.getInt("id"));
                cre.deposita(resultado.getDouble("saldo"));
            }
            // fecha conexão
            resultado.close();
            stmt.close();
            return cre;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Credito> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Credito> listar(String filtro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}