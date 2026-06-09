package com.template;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

public class ComponentesDAO {

    public ArrayList<ComponentesDTO> selectComponentes() {
        ArrayList<ComponentesDTO> listaComponentes = new ArrayList<>();
        String sql = "SELECT * FROM componentes";

        // Usando try-with-resources para garantir que tudo feche automaticamente
        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ComponentesDTO componentes = new ComponentesDTO();
                // CORREÇÃO: O nome da coluna no banco é id_pc
                componentes.setIdPc(rs.getInt("id_pc"));
                componentes.setNome(rs.getString("nome"));
                componentes.setGabinete(rs.getString("gabinete"));
                componentes.setCpu(rs.getString("cpu"));
                componentes.setGpu(rs.getString("gpu"));
                componentes.setRam(rs.getString("ram"));
                componentes.setDualchannel(rs.getBoolean("dualchannel"));
                // CORREÇÃO: O nome da coluna no banco é armazenamento
                componentes.setArmazenamento(rs.getString("armazenamento"));
                componentes.setBluetooth(rs.getBoolean("bluetooth"));

                listaComponentes.add(componentes);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ComponentesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaComponentes;
    }

    public void insertComponente(ComponentesDTO componentes) {
        String sql = "INSERT INTO componentes (nome, gabinete, cpu, gpu, ram, dualchannel, armazenamento, bluetooth) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, componentes.getNome());
            ps.setString(2, componentes.getGabinete());
            ps.setString(3, componentes.getCpu());
            ps.setString(4, componentes.getGpu());
            ps.setString(5, componentes.getRam());
            ps.setBoolean(6, componentes.isDualchannel());
            ps.setString(7, componentes.getArmazenamento());
            ps.setBoolean(8, componentes.isBluetooth());

            ps.execute()
            ;
        } catch (SQLException ex) {
            Logger.getLogger(ComponentesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateComponente(ComponentesDTO componentes) {
        String sql = "UPDATE componentes SET nome=?, gabinete=?, cpu=?, gpu=?, ram=?, dualchannel=?, armazenamento=?, bluetooth=? WHERE id_pc=?";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, componentes.getNome());
            ps.setString(2, componentes.getGabinete());
            ps.setString(3, componentes.getCpu());
            ps.setString(4, componentes.getGpu());
            ps.setString(5, componentes.getRam());
            ps.setBoolean(6, componentes.isDualchannel());
            ps.setString(7, componentes.getArmazenamento());
            ps.setBoolean(8, componentes.isBluetooth());
            ps.setInt(9, componentes.getIdPc());

            ps.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ComponentesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteComponente(ComponentesDTO componentes) {
        String sql = "DELETE FROM componentes WHERE id_pc=?";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, componentes.getIdPc());
            ps.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ComponentesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}