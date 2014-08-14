
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Brownie
 */
public class Altas extends javax.swing.JFrame {

    int idmo = 0, idarea = 0, idkasp = 0;
    int filasel;
    private static Connection cn;
    boolean todobien = true;
    PreparedStatement pst;
    StringTokenizer stok;
    ArrayList programas = new ArrayList();
    ArrayList <Area> arrayAreas = new ArrayList <Area>();
    ArrayList incidencias = new ArrayList();
//    ArrayList codareas = new ArrayList();
    ArrayList<Telefono> telefonos = new ArrayList<Telefono>();
    String[] titulos = {"C.Empleado", "Nombre", "A.Paterno", "A.Materno", "Área", "No.Serie", "No.Parte", "MAC", "S.O.", "Versión S.O.",
        "Licencia SO", "Adquisición", "Incidencias", "Versión MO",
        "Año MO", "Licencia MO", "Licencia Kasp", "Usuario", "Ver.Kaspersky",};
    String[] registro = new String[19];
    String[] editar = new String[19];
    DefaultTableModel modelo;
    Telefono tel;

    /**
     * Creates new form Altas
     */
    public Altas() {
        initComponents();
        setLocationRelativeTo(null);
        Configcbos();
        Inhabilitar();
        setTitle("Control de Equipos de la Universidad Autónoma de Campeche");
        modelo = new DefaultTableModel(null, titulos);
        tblRegistros.setModel(modelo);
        tblRegistros.enableInputMethods(false);
        tblRegistros.getTableHeader().setReorderingAllowed(false);
    }

    void Configcbos() {

        cboIncidencias.addItem("No");
        cboIncidencias.addItem("Si");
        cboTipoUsuario.addItem("Alumno");
        cboTipoUsuario.addItem("Docente");
        cboTipoUsuario.addItem("Adminisitrativo");
        cboSistemaOperativo.addItem("Windows Vista");
        cboSistemaOperativo.addItem("Windows XP");
        cboSistemaOperativo.addItem("Windows 7");
        cboSistemaOperativo.addItem("Windows 8");
        cboSistemaOperativo.addItem("Mac OS");
        cboVersionSo.addItem("Starter");
        cboVersionSo.addItem("Home Basic");
        cboVersionSo.addItem("Home Premium");
        cboVersionSo.addItem("Professional");
        cboVersionSo.addItem("Business");
        cboVersionSo.addItem("Enterprise");
        cboVersionSo.addItem("Ultimate");
        cboVersionSo.addItem("Mac OS");
        cboVersionSo.addItem("8.1");
        cboAdquisicion.addItem("Licitación");
        cboAdquisicion.addItem("Adjudicación Directa");
        cboAdquisicion.addItem("Licitación Restringida");
        cboAdquisicion.addItem("Desconocido");
        cboVersionKasp.addItem("Ver. 10");
        cboVersionKasp.addItem("Ver. 6.04");
        cboVersionMo.addItem("Home & Student");
        cboVersionMo.addItem("Home & Business");
        cboVersionMo.addItem("Standard");
        cboVersionMo.addItem("Professional");
        cboVersionMo.addItem("Professional Plus");
        cboVersionMo.addItem("Mac Student and Teacher");
        cboVersionMo.addItem("Mac Standard Edition");
        cboVersionMo.addItem("Mac Professional Edition");
        cboVersionMo.addItem("Mac 2008");
        cboVersionMo.addItem("Mac Home & Student");
        cboAnoMo.addItem("2004");
        cboAnoMo.addItem("2007");
        cboAnoMo.addItem("2008");
        cboAnoMo.addItem("2011");
        cboAnoMo.addItem("2013");
        cboAnoMo.addItem("2010");
        cboTelefono.addItem("No");
        cboTelefono.addItem("Si");
        cboModeloTelefono.addItem("IPTouch4018");
        cboModeloTelefono.addItem("IPTouch4028");
        cboModeloTelefono.addItem("IPTouch4068");
        cboModeloTelefono.addItem("IPTouch4038");
        cboModeloTelefono.addItem("IPTouch4020");
        ObtenerAreas();

    }
    
    public void Ordenar(){
        Comparator<Area> comp = new Comparator<Area>(){
            
            @Override
            public int compare(Area a, Area b){
                return (a.getArea()).compareTo((b.getArea()));
            }
        };
        Collections.sort(arrayAreas, comp);
    }

    void ObtenerAreas() {
        try {
            Statement st = cn.createStatement();
            ResultSet rse = st.executeQuery("SELECT * FROM siia.. CG_Vw_Uni_Responsables");
            while (rse.next()) {
//                codareas.add(rse.getString(1));
                arrayAreas.add(new Area(rse.getString(1), rse.getString(2)));
//                cboArea.addItem(rse.getString(2));
            }
            //ordenar
            Ordenar();
            //for para poner en el cbo
            for (int i = 0; i < arrayAreas.size(); i++) {
                cboArea.addItem(arrayAreas.get(i).getArea());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Altas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void Inhabilitar() {

        txtCodigoEmpleado.setEnabled(false);
        lblNombre.setEnabled(false);
        lblApellidoPaterno.setEnabled(false);
        lblApellidoMaterno.setEnabled(false);
        cboTipoUsuario.setEnabled(false);
        cboArea.setEnabled(false);
        txtNoSerie.setEnabled(false);
        txtNoParte.setEnabled(false);
        txtMac.setEnabled(false);
        cboSistemaOperativo.setEnabled(false);
        cboVersionSo.setEnabled(false);
        txtLicenciaSo.setEnabled(false);
        cboAdquisicion.setEnabled(false);
        cboIncidencias.setEnabled(false);
        cboVersionMo.setEnabled(false);
        cboAnoMo.setEnabled(false);
        txtLicenciaMo.setEnabled(false);
        txtLicenciaKasp.setEnabled(false);
        cboVersionKasp.setEnabled(false);

        btnCancelar.setEnabled(false);
        btnGuardar.setEnabled(false);

        txaProgramas.setEnabled(false);
        txaIncidencias.setEnabled(false);

        cboTelefono.setEnabled(false);
        txtMacTelefono.setEnabled(false);
        txtNoSerieTelefono.setEnabled(false);
        cboModeloTelefono.setEnabled(false);
    }

    void Habilitar() {

        txtCodigoEmpleado.setEnabled(true);
        lblNombre.setEnabled(true);
        lblApellidoPaterno.setEnabled(true);
        lblApellidoMaterno.setEnabled(true);
        cboTipoUsuario.setEnabled(true);
        cboArea.setEnabled(true);
        txtNoSerie.setEnabled(true);
        txtNoParte.setEnabled(true);
        txtMac.setEnabled(true);
        cboSistemaOperativo.setEnabled(true);
        cboVersionSo.setEnabled(true);
        txtLicenciaSo.setEnabled(true);
        cboAdquisicion.setEnabled(true);
        cboIncidencias.setEnabled(true);
        cboVersionMo.setEnabled(true);
        cboAnoMo.setEnabled(true);
        txtLicenciaMo.setEnabled(true);
        txtLicenciaKasp.setEnabled(true);
        cboVersionKasp.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnGuardar.setEnabled(true);
        txtCodigoEmpleado.requestFocus();

        txaProgramas.setEnabled(true);
        cboTelefono.setEnabled(true);
        

    }

    void Limpiar() {
        txtCodigoEmpleado.setText("");
        lblNombre.setText("");
        lblApellidoPaterno.setText("");
        lblApellidoMaterno.setText("");
        txtNoSerie.setText("");
        txtNoParte.setText("");
        txtMac.setText("");
        txtLicenciaSo.setText("");
        txtLicenciaMo.setText("");
        txtLicenciaKasp.setText("");

        txaProgramas.setText("");
        txaIncidencias.setText("");
        txtMacTelefono.setText("");
        txtNoSerieTelefono.setText("");

    }

    void AgregarAlaTabla() {

        registro[0] = txtCodigoEmpleado.getText();
        registro[1] = lblNombre.getText();
        registro[2] = lblApellidoPaterno.getText();
        registro[3] = lblApellidoMaterno.getText();
        registro[4] = arrayAreas.get(cboArea.getSelectedIndex()).getCodigo();
//        registro[4] = codareas.get((cboArea.getSelectedIndex())).toString();
        registro[5] = txtNoSerie.getText();
        registro[6] = txtNoParte.getText();
        registro[7] = txtMac.getText();
        registro[8] = cboSistemaOperativo.getSelectedItem().toString();
        registro[9] = cboVersionSo.getSelectedItem().toString();
        registro[10] = txtLicenciaSo.getText();
        registro[11] = cboAdquisicion.getSelectedItem().toString();
        registro[12] = cboIncidencias.getSelectedItem().toString();
        registro[13] = cboVersionMo.getSelectedItem().toString();
        registro[14] = cboAnoMo.getSelectedItem().toString();
        registro[15] = txtLicenciaMo.getText();
        registro[16] = txtLicenciaKasp.getText();
        registro[17] = cboTipoUsuario.getSelectedItem().toString();
        registro[18] = cboVersionKasp.getSelectedItem().toString();
        programas.add(txaProgramas.getText());
        incidencias.add(txaIncidencias.getText());
        tel = new Telefono();
        tel.setHay(cboTelefono.getSelectedItem().toString());
        tel.setMac(txtMacTelefono.getText());
        tel.setIdemp(Integer.parseInt(txtCodigoEmpleado.getText()));
        tel.setModelo(cboModeloTelefono.getSelectedItem().toString());
        tel.setNoserie(txtNoSerieTelefono.getText());

        modelo.addRow(registro);
        tblRegistros.setModel(modelo);
        telefonos.add(tel);

    }

    void AgregarAlaTabla(int row) {

        modelo.removeRow(row);
        programas.remove(row);
        incidencias.remove(row);
        telefonos.remove(row);

        registro[0] = txtCodigoEmpleado.getText();
        registro[1] = lblNombre.getText();
        registro[2] = lblApellidoPaterno.getText();
        registro[3] = lblApellidoMaterno.getText();
        registro[4] = arrayAreas.get(cboArea.getSelectedIndex()).getCodigo();
//        registro[4] = codareas.get((cboArea.getSelectedIndex())).toString();
        registro[5] = txtNoSerie.getText();
        registro[6] = txtNoParte.getText();
        registro[7] = txtMac.getText();
        registro[8] = cboSistemaOperativo.getSelectedItem().toString();
        registro[9] = cboVersionSo.getSelectedItem().toString();
        registro[10] = txtLicenciaSo.getText();
        registro[11] = cboAdquisicion.getSelectedItem().toString();
        registro[12] = cboIncidencias.getSelectedItem().toString();
        registro[13] = cboVersionMo.getSelectedItem().toString();
        registro[14] = cboAnoMo.getSelectedItem().toString();
        registro[15] = txtLicenciaMo.getText();
        registro[16] = txtLicenciaKasp.getText();
        registro[17] = cboTipoUsuario.getSelectedItem().toString();
        registro[18] = cboVersionKasp.getSelectedItem().toString();
        tel = new Telefono();
        tel.setHay(cboTelefono.getSelectedItem().toString());
        tel.setMac(txtMacTelefono.getText());
        tel.setIdemp(Integer.parseInt(txtCodigoEmpleado.getText()));
        tel.setModelo(cboModeloTelefono.getSelectedItem().toString());
        tel.setNoserie(txtNoSerieTelefono.getText());

        programas.add(txaProgramas.getText());
        incidencias.add(txaIncidencias.getText());
        modelo.addRow(registro);
        tblRegistros.setModel(modelo);
        telefonos.add(tel);

    }

    void BuscarEmpleadoEditar(int id) {
        modelo = (DefaultTableModel) tblRegistros.getModel();
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            editar[i] = (String) modelo.getValueAt(id, i);
        }
        txtCodigoEmpleado.setText(editar[0]);
        lblNombre.setText(editar[1]);
        lblApellidoPaterno.setText(editar[2]);
        lblApellidoMaterno.setText(editar[3]);
        txtNoSerie.setText(editar[5]);
        txtNoParte.setText(editar[6]);
        txtMac.setText(editar[7]);
        cboSistemaOperativo.setSelectedItem(editar[8]);
        cboVersionSo.setSelectedItem(editar[9]);
        txtLicenciaSo.setText(editar[10]);
        cboAdquisicion.setSelectedItem(editar[11]);
        cboIncidencias.setSelectedItem(editar[12]);
        cboVersionMo.setSelectedItem(editar[13]);
        cboAnoMo.setSelectedItem(editar[14]);
        txtLicenciaMo.setText(editar[15]);
        txtLicenciaKasp.setText(editar[16]);
        cboTipoUsuario.setSelectedItem(editar[17]);
        cboVersionKasp.setSelectedItem(editar[18]);
        txaProgramas.setText(programas.get(id).toString());
        txaIncidencias.setText(incidencias.get(id).toString());
        cboTelefono.setSelectedItem(telefonos.get(id).getHay());
        txtNoSerieTelefono.setText(telefonos.get(id).getNoserie());
        txtMacTelefono.setText(telefonos.get(id).getMac());
        cboModeloTelefono.setSelectedItem(telefonos.get(id).getModelo());
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        mnEditar = new javax.swing.JMenuItem();
        mnEliminar = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNoParte = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMac = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtLicenciaSo = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cboIncidencias = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        txtCodigoEmpleado = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        cboTipoUsuario = new javax.swing.JComboBox();
        cboSistemaOperativo = new javax.swing.JComboBox();
        cboVersionSo = new javax.swing.JComboBox();
        cboAdquisicion = new javax.swing.JComboBox();
        cboArea = new javax.swing.JComboBox();
        txtNoSerie = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        lblApellidoPaterno = new javax.swing.JLabel();
        lblApellidoMaterno = new javax.swing.JLabel();
        cboTelefono = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cboModeloTelefono = new javax.swing.JComboBox();
        txtMacTelefono = new javax.swing.JTextField();
        txtNoSerieTelefono = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtLicenciaMo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtLicenciaKasp = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        cboVersionKasp = new javax.swing.JComboBox();
        cboVersionMo = new javax.swing.JComboBox();
        cboAnoMo = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaProgramas = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaIncidencias = new javax.swing.JTextArea();
        jLabel22 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRegistros = new javax.swing.JTable();
        btnAgregarTodos = new javax.swing.JButton();

        mnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cog.png"))); // NOI18N
        mnEditar.setText("Modificar");
        mnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnEditarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mnEditar);

        mnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/IC82740.png"))); // NOI18N
        mnEliminar.setText("Eliminar");
        mnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnEliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mnEliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Captura de Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel14.setText("Programas: ");

        jLabel17.setText("Incidencias: ");

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardsar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/clear.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/door_exit_logout_log_out_sign_out-128pequeno.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuario y Equipos"));

        jLabel2.setText("Nombre:");

        jLabel19.setText("Apellido Paterno: ");

        jLabel20.setText("Apellido Materno: ");

        jLabel1.setText("Área o Dependencia: ");

        jLabel3.setText("No. Serie: ");

        txtNoParte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoParteActionPerformed(evt);
            }
        });

        jLabel4.setText("No. Parte: ");

        jLabel5.setText("MAC: ");

        txtMac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMacActionPerformed(evt);
            }
        });

        jLabel10.setText("SO: ");

        jLabel11.setText("Versión SO: ");

        jLabel13.setText("Forma de adquisición: ");

        jLabel12.setText("Licencia SO: ");

        txtLicenciaSo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLicenciaSoActionPerformed(evt);
            }
        });

        jLabel15.setText("Incidencias: ");

        cboIncidencias.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cboIncidencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboIncidenciasActionPerformed(evt);
            }
        });

        jLabel21.setText("Código Empleado: ");

        txtCodigoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoEmpleadoActionPerformed(evt);
            }
        });

        jLabel16.setText("Tipo de Usuario: ");

        cboTipoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoUsuarioActionPerformed(evt);
            }
        });

        cboSistemaOperativo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cboSistemaOperativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSistemaOperativoActionPerformed(evt);
            }
        });

        cboVersionSo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cboVersionSo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboVersionSoActionPerformed(evt);
            }
        });

        cboAdquisicion.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cboAdquisicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboAdquisicionActionPerformed(evt);
            }
        });

        cboArea.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cboArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboAreaActionPerformed(evt);
            }
        });

        txtNoSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoSerieActionPerformed(evt);
            }
        });

        lblNombre.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 0, 0));

        lblApellidoPaterno.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblApellidoPaterno.setForeground(new java.awt.Color(255, 0, 0));

        lblApellidoMaterno.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblApellidoMaterno.setForeground(new java.awt.Color(255, 0, 0));

        cboTelefono.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cboTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTelefonoActionPerformed(evt);
            }
        });

        jLabel23.setText("Teléfono: ");

        jLabel24.setText("Modelo: ");

        jLabel25.setText("MAC: ");

        jLabel26.setText("No. Serie: ");

        cboModeloTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboModeloTelefonoActionPerformed(evt);
            }
        });

        txtMacTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMacTelefonoActionPerformed(evt);
            }
        });

        txtNoSerieTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoSerieTelefonoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel16))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCodigoEmpleado, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cboTipoUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel12))
                                .addGap(60, 60, 60)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLicenciaSo)
                                    .addComponent(cboSistemaOperativo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboVersionSo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(cboModeloTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel24)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel15)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cboIncidencias, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel23)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cboTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel25)
                                                    .addComponent(jLabel26))
                                                .addGap(65, 65, 65)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtMacTelefono)
                                                    .addComponent(txtNoSerieTelefono))))
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboArea, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addGap(14, 14, 14)
                                    .addComponent(cboAdquisicion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel5))
                                    .addGap(46, 46, 46)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtMac, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNoParte)
                                            .addComponent(txtNoSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cboSistemaOperativo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cboVersionSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtLicenciaSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(cboIncidencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(cboModeloTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txtMacTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(txtNoSerieTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtCodigoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblNombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(lblApellidoPaterno))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(lblApellidoMaterno))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(cboTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNoSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNoParte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cboAdquisicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Microsoft Office y Kaspersky"));

        jLabel7.setText("Versión MO: ");

        jLabel6.setText("Año MO: ");

        jLabel8.setText("Licencia MO: ");

        txtLicenciaMo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLicenciaMoActionPerformed(evt);
            }
        });

        jLabel9.setText("Licencia Kaspersky: ");

        txtLicenciaKasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLicenciaKaspActionPerformed(evt);
            }
        });

        jLabel18.setText("Versión Kaspersky: ");

        cboVersionKasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboVersionKaspActionPerformed(evt);
            }
        });

        cboVersionMo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cboVersionMo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboVersionMoActionPerformed(evt);
            }
        });

        cboAnoMo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cboAnoMo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboAnoMoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtLicenciaMo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboVersionKasp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLicenciaKasp, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboVersionMo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboAnoMo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboVersionMo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboAnoMo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLicenciaMo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLicenciaKasp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboVersionKasp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txaProgramas.setColumns(20);
        txaProgramas.setRows(5);
        txaProgramas.setMaximumSize(new java.awt.Dimension(265, 2147483647));
        jScrollPane2.setViewportView(txaProgramas);

        txaIncidencias.setColumns(20);
        txaIncidencias.setRows(5);
        jScrollPane3.setViewportView(txaIncidencias);

        jLabel22.setText("Separe cada programa con \"Enter\"");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(76, 76, 76)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel17))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir))
                .addGap(21, 21, 21))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla de Registros"));

        tblRegistros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblRegistros.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(tblRegistros);

        btnAgregarTodos.setText("Agregar");
        btnAgregarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1186, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(531, 531, 531)
                .addComponent(btnAgregarTodos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregarTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void cboIncidenciasActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
        cboTelefono.requestFocus();
        if (cboIncidencias.getSelectedIndex() == 1) {
            txaIncidencias.setEnabled(true);
        } else {
            txaIncidencias.setEnabled(false);
        }
    }                                              

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        Habilitar();
        Limpiar();
    }                                        

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        Inhabilitar();
        Limpiar();
    }                                           

    private void txtNoParteActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        txtMac.requestFocus();
    }                                          

    private void txtMacActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
        cboAdquisicion.requestFocus();
    }                                      

    private void txtLicenciaMoActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
        txtLicenciaKasp.requestFocus();
    }                                             

    private void txtLicenciaKaspActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
        cboVersionKasp.requestFocus();
    }                                               

    private void txtLicenciaSoActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
        cboIncidencias.requestFocus();
    }                                             

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        int seleccion = JOptionPane.showOptionDialog(
                this, // Componente padre
                "¿Desea salir?", //Mensaje
                "Seleccione una opción", // Título
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, // null para icono por defecto.
                new Object[]{"Si", "No"}, // null para YES, NO y CANCEL
                "Si");
        if (seleccion != -1) {
            if ((seleccion + 1) == 1) {
                // PRESIONO SI
                this.dispose();
                try {
                    cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Altas.class.getName()).log(Level.SEVERE, null, ex);
                }
                LogIn a = new LogIn();
                a.setVisible(true);
            } else {
                // PRESIONO NOs+
            }
        }

    }                                        
    String accion = "Insertar";
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        if (accion.equals("Insertar")) {
            if (lblNombre.getText().equals("") || lblNombre.getText().equals("Código Incorrecto")) {
                JOptionPane.showMessageDialog(null, "El Código de Empleado es incorrecto", "Error", 0);

            } else {
                AgregarAlaTabla();
                Limpiar();
            }
        } else if (accion.equals("Modificar")) {
            if ( lblNombre.getText().equals("") || lblNombre.getText().equals("Código Incorrecto") ) {
                JOptionPane.showMessageDialog(null, "El Código de Empleado es incorrecto", "Error", 0);
            } else {

                if (filasel > modelo.getRowCount()) {
                    AgregarAlaTabla();
                    Limpiar();
                }
                AgregarAlaTabla(filasel);
                Limpiar();
                accion = "Insertar";
            }
        }
    }                                          

    private void mnEditarActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        try {
            filasel = tblRegistros.getSelectedRow();
            if (filasel == -1) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún registro", "Error", 0);
            } else {
                accion = "Modificar";
                BuscarEmpleadoEditar(filasel);
            }
        } catch (Exception e) {
        }
    }                                        

    private void txtCodigoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        try {
            // TODO add your handling code here:
            Statement st = cn.createStatement();
            ResultSet rse = st.executeQuery("SELECT  * FROM siia..RH_Vw_Datos_Empleados_Activos WHERE Id_Emp=" + txtCodigoEmpleado.getText());
            if (rse.next()) {
                lblNombre.setText(rse.getString(4));
                lblApellidoPaterno.setText(rse.getString(2));
                lblApellidoMaterno.setText(rse.getString(3));
                cboTipoUsuario.requestFocus();
            } else {
                lblNombre.setText("Código Incorrecto");
                lblApellidoPaterno.setText("Código Incorrecto");
                lblApellidoMaterno.setText("Código Incorrecto");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Altas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                                 

    private void mnEliminarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        try {
            filasel = tblRegistros.getSelectedRow();
            if (filasel == -1) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún registro", "Error", 0);
            } else {
                int seleccion = JOptionPane.showOptionDialog(
                        this, // Componente padre
                        "¿Desea eliminar este registro?", //Mensaje
                        "Seleccione una opción", // Título
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, // null para icono por defecto.
                        new Object[]{"Si", "No"}, // null para YES, NO y CANCEL
                        "Si");
                if (seleccion != -1) {
                    if ((seleccion + 1) == 1) {
                        // PRESIONO SI
                        programas.remove(filasel);
                        incidencias.remove(filasel);
                        telefonos.remove(filasel);

                        modelo = (DefaultTableModel) tblRegistros.getModel();
                        modelo.removeRow(filasel);
                        tblRegistros.setModel(modelo);
                    } else {
                        // PRESIONO NO
                    }
                }
            }
        } catch (Exception e) {
        }
    }                                          

    private void btnAgregarTodosActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
        int codemp, error = 0;
        int id = 0;
        ResultSet rs;
        Statement st;
        String programa, idprograma, incidencia, idincidencia, cadena;
        String sSQL = "";
        String sSQL2 = "SELECT @@identity";
        String nom, apepat, apemat, area, noserie, noparte, mac, so, verso, licso, adquis, legal,
                vermo, licmo, lickasp, anomo, tipous, verkasp, noserietel, modelotel, mactel;

        int seleccion = JOptionPane.showOptionDialog(
                this, // Componente padre
                "Está a punto de agregar los registros a la Base de Datos ¿Desea agregarlos?", //Mensaje
                "Seleccione una opción", // Título
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, // null para icono por defecto.
                new Object[]{"Si", "No"}, // null para YES, NO y CANCEL
                "Si");
        if (seleccion != -1) {
            if ((seleccion + 1) == 1) {
                // PRESIONO SI
                int filas = modelo.getRowCount() - 1;//obtener numero de registros en tabla
                a:
                for (int i = filas; i >= 0; i--) {
                    todobien = true;
                    //Agregar a tablaMO
                    //Valores
                    //ID_MO
                    licmo = (String) modelo.getValueAt(i, 15);
                    vermo = (String) modelo.getValueAt(i, 13);
                    anomo = (String) modelo.getValueAt(i, 14);

                    sSQL = "begin tran "
                            + "INSERT INTO IS_Microsoftoffice(Licencia, Version, Ano)"
                            + "VALUES(?, ?, ?)";
                    try {
                        pst = cn.prepareStatement(sSQL);
                        //El Id es autoincrementable
                        pst.setString(1, licmo);
                        pst.setString(2, vermo);
                        pst.setString(3, anomo);

                        int n = pst.executeUpdate();
                    } catch (SQLException ex) {
                        todobien = false;
                        JOptionPane.showMessageDialog(null, ex);
                    }
//Agregar a Sistemaoperativo
                    
                    so = (String) modelo.getValueAt(i, 8);
                    verso = (String) modelo.getValueAt(i, 9);
                    licso = (String) modelo.getValueAt(i, 10);
                    
                    sSQL ="INSERT INTO IS_Sistemaoperativo(Sistemaoperativo,Version,Licencia)"
                            + "VALUES(?, ?, ?)";
                    try {
                        pst = cn.prepareStatement(sSQL);
                        //El Id es autoincrementable
                        pst.setString(1, so);
                        pst.setString(2, verso);
                        pst.setString(3, licso);

                        int n = pst.executeUpdate();
                    } catch (SQLException ex) {
                        todobien = false;
                        JOptionPane.showMessageDialog(null, ex);
                    }
                    
                    
//Agregar a TablaKasp
                    //ID_Kasp
                    lickasp = (String) modelo.getValueAt(i, 16);
                    verkasp = (String) modelo.getValueAt(i, 18);

                    sSQL = "INSERT INTO IS_Kaspersky(Licencia, Version)"
                            + "VALUES(?, ?)";
                    try {

                        st = cn.createStatement();
                        rs = st.executeQuery(sSQL2);//Pido el ultimo valor agregado
                        rs.next();
                        id = rs.getInt(1);//guardo el ultimo valor agregado
                        pst = cn.prepareStatement(sSQL);
                        //El id es autoincrementable
                        pst.setString(1, lickasp);
                        pst.setString(2, verkasp);
                        int n = pst.executeUpdate();
                    } catch (SQLException ex) {
                        todobien = false;
                        JOptionPane.showMessageDialog(null, ex);
                    }
//Agregar a TablaEquipo        
                    codemp = Integer.parseInt((String) modelo.getValueAt(i, 0));
                    nom = (String) modelo.getValueAt(i, 1);
                    apepat = (String) modelo.getValueAt(i, 2);
                    apemat = (String) modelo.getValueAt(i, 3);
                    tipous = (String) modelo.getValueAt(i, 17);
                    noserie = (String) modelo.getValueAt(i, 5);
                    noparte = (String) modelo.getValueAt(i, 6);
                    mac = (String) modelo.getValueAt(i, 7);
                    area = (String) modelo.getValueAt(i, 4);
                    //ID_Empleado
                    //ID_MO
                    //ID_Kasp

                    sSQL = "INSERT INTO IS_Equipos(No_Serie, No_Parte, Mac, Id_Empleado,"
                            + "Tipo_Usuario, Id_Microsoftoffice, Id_Kaspersky, Id_Area, Id_Sistemaoperativo)"
                            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try {
                        pst = cn.prepareStatement(sSQL);
                        pst.setString(1, noserie);
                        pst.setString(2, noparte);
                        pst.setString(3, mac);
                        pst.setInt(4, codemp);
                        pst.setString(5, tipous);
                        pst.setInt(6, id); //idmo 
                        pst.setInt(7, id); //iskasp
                        pst.setInt(8, Integer.parseInt(area)); //idarea
                        pst.setInt(9, id);//idSo
                        int n = pst.executeUpdate();
                    } catch (SQLException ex) {
                        todobien = false;
                        JOptionPane.showMessageDialog(null, ex);
                    }
                    //ERROR?
                    if (!todobien) {
                        try {
                            st = cn.createStatement();
                            st.executeQuery("rollback tran");
                        } catch (SQLException ex) {
                            Logger.getLogger(Altas.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        continue a;
                    }

//TablaProgramas
                    cadena = programas.get(i).toString();
                    //creo stringtokenizer para analizar el texto del textArea
                    stok = new StringTokenizer(cadena, "\n");
                    while (stok.hasMoreTokens()) {
                        //Leo cada renglon
                        programa = stok.nextToken();
                        //concateno programa y no serie de equipo para crear el ID
                        idprograma = noserie + programa;
                        //agrego cada renglon a la tabla como programa individual
                        sSQL = "INSERT INTO IS_Programas(Id_Programa, No_Serie, Programa)"
                                + "VALUES(?, ?, ?)";
                        try {
                            pst = cn.prepareStatement(sSQL);
                            pst.setString(1, idprograma);
                            pst.setString(2, noserie);
                            pst.setString(3, programa);
                            int n = pst.executeUpdate();
                        } catch (SQLException ex) {
                            todobien = false;
                            JOptionPane.showMessageDialog(null, ex);
                        }
                    }

                    //ERROR
                    if (!todobien) {
                        try {
                            st = cn.createStatement();
                            st.executeQuery("rollback tran");
                        } catch (SQLException ex) {
                            Logger.getLogger(Altas.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        continue a;
                    }

//TablaIncidencias
                    cadena = incidencias.get(i).toString();
                    //creo stringtokenizer para analizar el texto del textArea
                    stok = new StringTokenizer(cadena, "\n");
                    while (stok.hasMoreTokens()) {
                        //Leo cada renglon
                        incidencia = stok.nextToken();
                        //concateno incidencia y no serie para crear ID de la tabla
                        idincidencia = noserie + incidencia;
                        sSQL = "INSERT INTO IS_Incidencias(Id_Incidencia, No_Serie, Incidencia)"
                                + "VALUES(?, ?, ?)";
                        try {
                            pst = cn.prepareStatement(sSQL);
                            pst.setString(1, idincidencia);
                            pst.setString(2, noserie);
                            pst.setString(3, incidencia);
                            int n = pst.executeUpdate();
                        } catch (SQLException ex) {
                            todobien = false;
                            JOptionPane.showMessageDialog(null, ex);
                        }
                    }
                    //ERROR
                    if (!todobien) {
                        try {
                            st = cn.createStatement();
                            st.executeQuery("rollback tran");

                        } catch (SQLException ex) {
                            Logger.getLogger(Altas.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        continue a;
                    }
                    //Agregar a telefonos
                    if (telefonos.get(i).getHay().equals("Si")) {
                        noserietel = telefonos.get(i).getNoserie();
                        mactel = telefonos.get(i).getMac();
                        modelotel = telefonos.get(i).getModelo();

                        sSQL = "INSERT INTO IS_Telefonos(No_Serie, Mac, Modelo, Id_Empleado)"
                                + "VALUES(?, ?, ?, ?)";
                        try {
                            pst = cn.prepareStatement(sSQL);
                            pst.setString(1, noserietel);
                            pst.setString(2, mactel);
                            pst.setString(3, modelotel);
                            pst.setInt(4, codemp);
                            int n = pst.executeUpdate();
                        } catch (SQLException ex) {
                            todobien = false;
                            JOptionPane.showMessageDialog(null, ex);

                        }
                    }
                    if (!todobien) {
                        JOptionPane.showMessageDialog(null, "Ha habido un error al agregar este registro a la BD", "Error", 0);
                    } else {
                        try {
                            modelo.removeRow(i);
                            programas.remove(i);
                            incidencias.remove(i);
                            telefonos.remove(i);
                            st = cn.createStatement();
                            st.executeQuery("commit tran");
                            JOptionPane.showMessageDialog(null, "Registro agregado.", "Éxito", 1);
                            //elimino ese registro del modelo pue ya fue agregado a la base de datos
                        } catch (SQLException ex) {
                            Logger.getLogger(Altas.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }//termina el for
                if (todobien) {
                    JOptionPane.showMessageDialog(null, "Registros agregados.", "Éxito", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Ha habido un error al agregar este registro a la BD", "Error", 0);
                }
            }
        } else {
            // PRESIONO NO
        }
    }                                               

    private void cboTipoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
        txtNoSerie.requestFocus();
    }                                              

    private void cboSistemaOperativoActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // TODO add your handling code here:
        cboVersionSo.requestFocus();
    }                                                   

    private void cboVersionSoActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        txtLicenciaSo.requestFocus();
    }                                            

    private void cboAdquisicionActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
        cboSistemaOperativo.requestFocus();
    }                                              

    private void cboVersionMoActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        cboAnoMo.requestFocus();
    }                                            

    private void cboAnoMoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        txtLicenciaMo.requestFocus();
    }                                        

    private void cboVersionKaspActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void cboAreaActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        txtCodigoEmpleado.requestFocus();
    }                                       

    private void txtNoSerieActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        txtNoParte.requestFocus();
    }                                          

    private void cboTelefonoActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        if (cboTelefono.getSelectedItem().equals("Si")) {
            cboModeloTelefono.setEnabled(true);
            txtNoSerieTelefono.setEnabled(true);
            txtMacTelefono.setEnabled(true);
            cboModeloTelefono.requestFocus();
        } else {
            cboModeloTelefono.setEnabled(false);
            txtNoSerieTelefono.setEnabled(false);
            txtMacTelefono.setEnabled(false);
            cboVersionMo.requestFocus();
        }
    }                                           

    private void cboModeloTelefonoActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        txtMacTelefono.requestFocus();
    }                                                 

    private void txtMacTelefonoActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
        txtNoSerieTelefono.requestFocus();
    }                                              

    private void txtNoSerieTelefonoActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
        cboVersionMo.requestFocus();
    }                                                  

    /**
     * @param args the command line arguments
     */
    public static void crear(Connection cn) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Altas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Altas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Altas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Altas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        Altas.cn = cn;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Altas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAgregarTodos;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cboAdquisicion;
    private javax.swing.JComboBox cboAnoMo;
    private javax.swing.JComboBox cboArea;
    private javax.swing.JComboBox cboIncidencias;
    private javax.swing.JComboBox cboModeloTelefono;
    private javax.swing.JComboBox cboSistemaOperativo;
    private javax.swing.JComboBox cboTelefono;
    private javax.swing.JComboBox cboTipoUsuario;
    private javax.swing.JComboBox cboVersionKasp;
    private javax.swing.JComboBox cboVersionMo;
    private javax.swing.JComboBox cboVersionSo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblApellidoMaterno;
    private javax.swing.JLabel lblApellidoPaterno;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JMenuItem mnEditar;
    private javax.swing.JMenuItem mnEliminar;
    private javax.swing.JTable tblRegistros;
    private javax.swing.JTextArea txaIncidencias;
    private javax.swing.JTextArea txaProgramas;
    private javax.swing.JTextField txtCodigoEmpleado;
    private javax.swing.JTextField txtLicenciaKasp;
    private javax.swing.JTextField txtLicenciaMo;
    private javax.swing.JTextField txtLicenciaSo;
    private javax.swing.JTextField txtMac;
    private javax.swing.JTextField txtMacTelefono;
    private javax.swing.JTextField txtNoParte;
    private javax.swing.JTextField txtNoSerie;
    private javax.swing.JTextField txtNoSerieTelefono;
    // End of variables declaration                   
}
