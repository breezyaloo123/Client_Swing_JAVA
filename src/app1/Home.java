/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app1;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import app1.Etudiant;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.json.JSONObject;

/**
 *
 * @author LENOVO
 */
public class Home extends javax.swing.JFrame {

    Context c;
    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        try {
            getEtudiants();
            getNotes();
            getStages();
            jButton1.setVisible(false);
            
        } catch (Exception ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
    
    
    public void insertDatas(String num_etudiant,String prenom,String nom) throws IOException
    {
            URL url = null;
        try {
            url = new URL("http://localhost:8080/mysql/addEtudiant?num_etudiant="+num_etudiant+"&prenom="+prenom+"&nom="+nom);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            int d = con.getResponseCode();
            System.out.println(""+d);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }
    
    public void insertNote(String num_etudiant,String nom_matiere,int note)
    {
         URL url = null;
        try {
            url = new URL("http://localhost:9091/postgres/addNote?num_etudiant="+num_etudiant+"&nom_matiere="+nom_matiere+"&note="+note);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            int d = con.getResponseCode();
            System.out.println(""+d);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertStage(String num_etudiant,String prenom,String nom,String nom_entreprise)
    {
        URL url = null;
        try {
            url = new URL("http://localhost:9090/mariadb/addStage?num_etudiant="+num_etudiant+"&prenom="+prenom+"&nom="+nom+"&nom_entreprise="+nom_entreprise);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            int d = con.getResponseCode();
            System.out.println(""+d);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    public void getEtudiants() throws Exception
    {
        
       try {
                
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet("http://localhost:8080/mysql/etudiants");
		getRequest.addHeader("accept", "application/json");
		HttpResponse response = httpClient.execute(getRequest);
		
		if (response.getStatusLine().getStatusCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : " +
		response.getStatusLine().getStatusCode());
		}
		BufferedReader br;
                String prenom = "";
                String nom = "";
                String num_etudiant= "";
                Object[] row1 = null;
                DefaultTableModel d = new DefaultTableModel();
                List<String> list = new ArrayList<String>();
                
              //  Object[] a = null;
          //      String[] columnNames = { "Num_Etudiant", "Prenom", "Nom"};
               // DefaultTableModel mod = new DefaultTableModel(columnNames,0);
                
                
                ArrayList<Etudiant> et = new ArrayList<>();

			br = new BufferedReader(new
			InputStreamReader((response.getEntity().getContent())));
			
			String output;
                        String res;
                        JSONObject r =null;
                        JSONArray array = null;
			while ((output = br.readLine()) != null) {
			System.out.println(output);
                        res = "{'etudiant':"+output+"}";
                        r = new JSONObject(res);
                        
                            System.out.println(r);
                        array= r.getJSONArray("etudiant");
                        
     
			}
                        
                         for(int i= 0;i<array.length();i++)
                        {
//                            if(array.get(i) instanceof JSONObject)
//                            {
//                              JSONObject dd = (JSONObject) array.get(i);
//                              num_etudiant = (String) dd.get("num_etudiant");
//                              prenom = (String) dd.get("prenom");
//                              nom = (String) dd.get("nom");
//                                System.out.println(prenom+" "+nom);
//                                
//                                
//                                 
//                            }
                            
                            num_etudiant = array.getJSONObject(i).getString("num_etudiant");
                            prenom = array.getJSONObject(i).getString("prenom");
                            nom = array.getJSONObject(i).getString("nom");
                            
                            Etudiant etudiant = new Etudiant(num_etudiant, prenom, nom);
                            
                            et.add(etudiant);
//                            list.add(num_etudiant);
//                            list.add(prenom);
//                            list.add(nom);
                            System.out.println("First client is = "+array.getJSONObject(i).getString("prenom"));
                            
                        }
                         
                         System.out.println(et.size());
                         
                         DefaultTableModel f = (DefaultTableModel) jTable1.getModel();
                         
                        // System.out.println(list.size());
                      //   f.insertRow(list.toArr, list.toArray());
                         
//                        for(int i= 0;i<et.size();i++)
//                        {
//                          // a = {et.get(i).getNum_etudiant(),et.get(i).getPrenom(),et.get(i).getNom()};
//                            
//                            list.add(et.get(i).getNum_etudiant());
//                            list.add(et.get(i).getPrenom());
//                            list.add(et.get(i).getNom());
//                        }
                        
                      
                        for(Etudiant rr: et)
                        {
                            Vector<String> v = new Vector<String>();
                            v.add(rr.getNum_etudiant());
                            v.add(rr.getPrenom());
                            v.add(rr.getNom());
                            f.addRow(v);
                        }
                        f.fireTableDataChanged();
                        
//                        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//                    @Override
//                    public void valueChanged(ListSelectionEvent e) throws UnsupportedOperationException{
//                      //System.out.println(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
//                      String num = jTable1.getValueAt(jTable1.getSelectedRow(),0).toString();
//                       // System.out.println(num);
//                        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                    }
//                });
                      //   jTable1.setModel(d);
                      
                      
                      
                  //    f.addRow(list.toArray());
                         
                        
                         
                        
                         
                     
                        
			
		} catch (UnsupportedOperationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    public void getStages()
    {
         try {
                
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet("http://localhost:9090/mariadb/etudiants");
		getRequest.addHeader("accept", "application/json");
		HttpResponse response = httpClient.execute(getRequest);
		
		if (response.getStatusLine().getStatusCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : " +
		response.getStatusLine().getStatusCode());
		}
		BufferedReader br;
                String prenom = "";
                String nom = "";
                String nom_entreprise ="";
                int note;
                String num_etudiant= "";
                Object[] row1 = null;
                DefaultTableModel d = new DefaultTableModel();
                List<String> list = new ArrayList<String>();
                
              //  Object[] a = null;
          //      String[] columnNames = { "Num_Etudiant", "Prenom", "Nom"};
               // DefaultTableModel mod = new DefaultTableModel(columnNames,0);
                
                
                ArrayList<EtudiantStage> et = new ArrayList<>();

			br = new BufferedReader(new
			InputStreamReader((response.getEntity().getContent())));
			
			String output;
                        String res;
                        JSONObject r =null;
                        JSONArray array = null;
			while ((output = br.readLine()) != null) {
			System.out.println(output);
                        res = "{'etudiant':"+output+"}";
                        r = new JSONObject(res);
                        
                            System.out.println(r);
                        array= r.getJSONArray("etudiant");
                        
     
			}
                        
                         for(int i= 0;i<array.length();i++)
                        {
//                            if(array.get(i) instanceof JSONObject)
//                            {
//                              JSONObject dd = (JSONObject) array.get(i);
//                              num_etudiant = (String) dd.get("num_etudiant");
//                              prenom = (String) dd.get("prenom");
//                              nom = (String) dd.get("nom");
//                                System.out.println(prenom+" "+nom);
//                                
//                                
//                                 
//                            }
                            
                            num_etudiant = array.getJSONObject(i).getString("num_etudiant");
                            prenom = array.getJSONObject(i).getString("prenom");
                            nom = array.getJSONObject(i).getString("nom");
                            nom_entreprise = array.getJSONObject(i).getString("nom_entreprise");
                            
                            EtudiantStage stage = new EtudiantStage(num_etudiant, prenom, nom, nom_entreprise);
                            
                            et.add(stage);
//                            list.add(num_etudiant);
//                            list.add(prenom);
//                            list.add(nom);
                            System.out.println("First client is = "+array.getJSONObject(i).getString("num_etudiant"));
                            
                        }
                         
                         System.out.println(et.size());
                         
                         DefaultTableModel f = (DefaultTableModel) jTable3.getModel();
                         
                        // System.out.println(list.size());
                      //   f.insertRow(list.toArr, list.toArray());
                         
//                        for(int i= 0;i<et.size();i++)
//                        {
//                          // a = {et.get(i).getNum_etudiant(),et.get(i).getPrenom(),et.get(i).getNom()};
//                            
//                            list.add(et.get(i).getNum_etudiant());
//                            list.add(et.get(i).getPrenom());
//                            list.add(et.get(i).getNom());
//                        }
                        
                      
                        for(EtudiantStage rr: et)
                        {
                            Vector<String> v = new Vector<String>();
                            v.add(rr.getNum_etudiant());
                            v.add(rr.getPrenom());
                            v.add(rr.getNom());
                            v.add(rr.getNom_entreprise());
                            f.addRow(v);
                        }
                        
//                        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//                    @Override
//                    public void valueChanged(ListSelectionEvent e) throws UnsupportedOperationException{
//                      //System.out.println(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
//                      String num = jTable1.getValueAt(jTable1.getSelectedRow(),0).toString();
//                       // System.out.println(num);
//                        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                    }
//                });
                      //   jTable1.setModel(d);
                      
                      
                      
                  //    f.addRow(list.toArray());
                         
                        
                         
                        
                         
                     
                        
			
		} catch (UnsupportedOperationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void getNotes()
    {
        try {
                
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet("http://localhost:9091/postgres/getSub");
		getRequest.addHeader("accept", "application/json");
		HttpResponse response = httpClient.execute(getRequest);
		
		if (response.getStatusLine().getStatusCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : " +
		response.getStatusLine().getStatusCode());
		}
		BufferedReader br;
                String nom_matiere = "";
                int note;
                String num_etudiant= "";
                Object[] row1 = null;
                DefaultTableModel d = new DefaultTableModel();
                List<String> list = new ArrayList<String>();
                
              //  Object[] a = null;
          //      String[] columnNames = { "Num_Etudiant", "Prenom", "Nom"};
               // DefaultTableModel mod = new DefaultTableModel(columnNames,0);
                
                
                ArrayList<EtudiantNote> et = new ArrayList<>();

			br = new BufferedReader(new
			InputStreamReader((response.getEntity().getContent())));
			
			String output;
                        String res;
                        JSONObject r =null;
                        JSONArray array = null;
			while ((output = br.readLine()) != null) {
			System.out.println(output);
                        res = "{'etudiant':"+output+"}";
                        r = new JSONObject(res);
                        
                            System.out.println(r);
                        array= r.getJSONArray("etudiant");
                        
     
			}
                        
                         for(int i= 0;i<array.length();i++)
                        {
//                            if(array.get(i) instanceof JSONObject)
//                            {
//                              JSONObject dd = (JSONObject) array.get(i);
//                              num_etudiant = (String) dd.get("num_etudiant");
//                              prenom = (String) dd.get("prenom");
//                              nom = (String) dd.get("nom");
//                                System.out.println(prenom+" "+nom);
//                                
//                                
//                                 
//                            }
                            
                            num_etudiant = array.getJSONObject(i).getString("num_etudiant");
                            nom_matiere = array.getJSONObject(i).getString("nom_matiere");
                            note = array.getJSONObject(i).getInt("note");
                            
                            EtudiantNote etudiant = new EtudiantNote(num_etudiant, nom_matiere,note);
                            
                            et.add(etudiant);
//                            list.add(num_etudiant);
//                            list.add(prenom);
//                            list.add(nom);
                            System.out.println("First client is = "+array.getJSONObject(i).getString("num_etudiant"));
                            
                        }
                         
                         System.out.println(et.size());
                         
                         DefaultTableModel f = (DefaultTableModel) jTable2.getModel();
                         
                        // System.out.println(list.size());
                      //   f.insertRow(list.toArr, list.toArray());
                         
//                        for(int i= 0;i<et.size();i++)
//                        {
//                          // a = {et.get(i).getNum_etudiant(),et.get(i).getPrenom(),et.get(i).getNom()};
//                            
//                            list.add(et.get(i).getNum_etudiant());
//                            list.add(et.get(i).getPrenom());
//                            list.add(et.get(i).getNom());
//                        }
                        
                      
                        for(EtudiantNote rr: et)
                        {
                            Vector<String> v = new Vector<String>();
                            v.add(rr.getNum_etudiant());
                            v.add(rr.getNom_matiere());
                            String nn = ""+rr.getNote();
                            v.add(nn);
                            f.addRow(v);
                        }
                        
//                        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//                    @Override
//                    public void valueChanged(ListSelectionEvent e) throws UnsupportedOperationException{
//                      //System.out.println(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
//                      String num = jTable1.getValueAt(jTable1.getSelectedRow(),0).toString();
//                       // System.out.println(num);
//                        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                    }
//                });
                      //   jTable1.setModel(d);
                      
                      
                      
                  //    f.addRow(list.toArray());
                         
                        
                         
                        
                         
                     
                        
			
		} catch (UnsupportedOperationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTION DES ETUDIANTS");
        setBackground(new java.awt.Color(0, 102, 102));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Num_Etudiant", "Prenom", "Nom"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setText("num_etudiant");

        jTextField2.setText("prenom");

        jTextField3.setText("nom");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 255, 255));
        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton2.setText("INSCRIRE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(51, 51, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setText("Inscription d'un Etudiant");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                        .addGap(49, 49, 49)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jTextField3))
                .addGap(32, 32, 32))
        );

        jTextField4.setText("num_etudiant");

        jTextField5.setText("nom_matiere");
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jTextField6.setText("note");
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(153, 153, 0));
        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton3.setText("NOTE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jTextField8.setText("prenom");

        jTextField9.setText("nom");

        jTextField10.setText("Nom de l'entreprise");

        jButton4.setBackground(new java.awt.Color(255, 255, 153));
        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton4.setText("STAGE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Num_etudiant", "Nom_matiere", "Note"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Num_Etudiant", "Prenom", "Nom", "Nom_Entreprise"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setText("Attribution d'un Stage");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel3.setText("Attribution d'une Note");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setText("La Liste des Notes");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel5.setText("La Liste des Etudiants inscrits");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel6.setText("La Liste des Stages");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(35, 35, 35)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jButton1)
                                            .addGap(319, 319, 319))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(81, 81, 81)))
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(101, 101, 101)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jTextField9)
                                                .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextField10, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(48, 48, 48)
                                            .addComponent(jButton4)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(189, 189, 189)
                                .addComponent(jLabel3)))
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 648, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(388, 388, 388))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(325, 325, 325))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(102, 102, 102)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1364, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton1)
                                        .addGap(106, 106, 106)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(29, 29, 29)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(212, 212, 212))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(320, 320, 320))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jLabel5)
                    .addContainerGap(932, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("empty-statement")
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
                
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet("http://localhost:8080/mysql/etudiants");
		getRequest.addHeader("accept", "application/json");
		HttpResponse response = httpClient.execute(getRequest);
		
		if (response.getStatusLine().getStatusCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : " +
		response.getStatusLine().getStatusCode());
		}
		BufferedReader br;
                String prenom = "";
                String nom = "";
                String num_etudiant= "";
                Object[] row1 = null;
                 DefaultTableModel d = new DefaultTableModel();
                List<String> list = new ArrayList<String>();
                
                Object[] a = null;
                String[] columnNames = { "Num_Etudiant", "Prenom", "Nom"};
               // DefaultTableModel mod = new DefaultTableModel(columnNames,0);
                
                
                ArrayList<Etudiant> et = new ArrayList<>();

			br = new BufferedReader(new
			InputStreamReader((response.getEntity().getContent())));
			
			String output;
                        String res;
                        JSONObject r =null;
                        JSONArray array = null;
			while ((output = br.readLine()) != null) {
			System.out.println(output);
                        res = "{'etudiant':"+output+"}";
                        r = new JSONObject(res);
                        
                            System.out.println(r);
                        array= r.getJSONArray("etudiant");
                        
     
			}
                        
                         for(int i= 0;i<array.length();i++)
                        {
//                            if(array.get(i) instanceof JSONObject)
//                            {
//                              JSONObject dd = (JSONObject) array.get(i);
//                              num_etudiant = (String) dd.get("num_etudiant");
//                              prenom = (String) dd.get("prenom");
//                              nom = (String) dd.get("nom");
//                                System.out.println(prenom+" "+nom);
//                                
//                                
//                                 
//                            }
                            
                            num_etudiant = array.getJSONObject(i).getString("num_etudiant");
                            prenom = array.getJSONObject(i).getString("prenom");
                            nom = array.getJSONObject(i).getString("nom");
                            
                            Etudiant etudiant = new Etudiant(num_etudiant, prenom, nom);
                            
                            et.add(etudiant);
//                            list.add(num_etudiant);
//                            list.add(prenom);
//                            list.add(nom);
                            System.out.println("First client is = "+array.getJSONObject(i).getString("prenom"));
                            
                        }
                         
                         System.out.println(et.size());
                         
                         DefaultTableModel f = (DefaultTableModel) jTable1.getModel();
                         
                        // System.out.println(list.size());
                      //   f.insertRow(list.toArr, list.toArray());
                         
                        for(int i= 0;i<et.size();i++)
                        {
                          // a = {et.get(i).getNum_etudiant(),et.get(i).getPrenom(),et.get(i).getNom()};
                            
                            list.add(et.get(i).getNum_etudiant());
                            list.add(et.get(i).getPrenom());
                            list.add(et.get(i).getNom());
                        }
                        
                      
                        for(Etudiant rr: et)
                        {
                            Vector<String> v = new Vector<String>();
                            v.add(rr.getNum_etudiant());
                            v.add(rr.getPrenom());
                            v.add(rr.getNom());
                            f.addRow(v);
                        }
                      //   jTable1.setModel(d);
                      
                      
                      
                  //    f.addRow(list.toArray());
                         
                        
                         
                        
                         
                     
                        
			
		} catch (UnsupportedOperationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        DefaultTableModel ff = (DefaultTableModel) jTable1.getModel();
        
        String num_etudiant1 = jTextField1.getText().toString();
        String prenom1 = jTextField2.getText().toString();
        String nom1 = jTextField3.getText().toString();
        
        try {
            insertDatas(num_etudiant1, prenom1, nom1);
            try {
                ff.setRowCount(0);
                getEtudiants();
            } catch (Exception ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
            ff.fireTableDataChanged();
            
            
            
            
//        try {
//            URL url = new URL("http://localhost:8080/mysql/add1");
//            HttpURLConnection con = (HttpURLConnection)url.openConnection();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Content-Type", "application/json; utf-8");
//            con.setRequestProperty("Accept", "application/json");
//            con.setDoOutput(true);
//            String jsonInputString = "{\"num_etudiant\":'"+num_etudiant1+"', \"prenom\": '"+prenom1+"',\"nom\": '"+nom1+"'}";
//            try(OutputStream os = con.getOutputStream()){
//			byte[] input = jsonInputString.getBytes("utf-8");
//			os.write(input, 0, input.length);			
//		}
//            int code = con.getResponseCode();
//		System.out.println(code);
//		
//		try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))){
//			StringBuilder response = new StringBuilder();
//			String responseLine = null;
//			while ((responseLine = br.readLine()) != null) {
//				response.append(responseLine.trim());
//			}
//			System.out.println(response.toString());
//		}
//            String jsonRequest = "{}";
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        JTable source = (JTable)evt.getSource();
        int row = source.rowAtPoint(evt.getPoint());
        int column = source.columnAtPoint(evt.getPoint());
        
       // String s = source.getModel().getValueAt(row, column).toString();
        
        
     //   String  = source.getModel().getValueAt(row, 1).toString();
     
     int row1 = jTable1.getSelectedRow();
     
     String prenom = jTable1.getModel().getValueAt(row1, 1).toString();
     String nom = jTable1.getModel().getValueAt(row1, 2).toString();
     String s = jTable1.getModel().getValueAt(row1, 0).toString();
        
        jTextField4.setText(s);
        jTextField7.setText(s);
        jTextField8.setText(prenom);
        jTextField9.setText(nom);
        System.out.println(s);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel ff = (DefaultTableModel) jTable2.getModel();
        String num_etudiant = jTextField4.getText().toString();
        String nom_matiere = jTextField5.getText().toString().trim();
        
        int note = Integer.parseInt(jTextField6.getText().toString());
        
        insertNote(num_etudiant, nom_matiere, note);
        ff.setRowCount(0);
        getNotes();
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel ff = (DefaultTableModel) jTable3.getModel();
        String num_etudiant = jTextField7.getText().toString();
        String prenom = jTextField8.getText().toString();
        String nom = jTextField9.getText().toString();
        String nom_entreprise = jTextField10.getText().toString();
        
        
        
        
        System.out.println(num_etudiant);
        System.out.println(prenom);
        System.out.println(nom);
        System.out.println(nom_entreprise);
        
        insertStage(num_etudiant, prenom, nom, nom_entreprise);
        ff.setRowCount(0);
        getStages();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
