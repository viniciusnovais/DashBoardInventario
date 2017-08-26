package br.com.pdasolucoes.dashboardinventario.Services;


import android.content.SharedPreferences;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import br.com.pdasolucoes.dashboardinventario.ConfiguracoesActivity;
import br.com.pdasolucoes.dashboardinventario.DashBoardModelo;

/**
 * Created by PDA on 04/07/2017.
 */

public class Service {

    public static String ERROR = "";
    private static String servidor, diretorio;

    public static int GetInventario(String autorizaocao, SharedPreferences preferences) {

        servidor = preferences.getString("servidor", "");
        diretorio = preferences.getString("diretorio", "");

        //String URL = "http://179.184.159.52/invtablet/wsinventario.asmx";
        String URL = "http://" + servidor + "/" + diretorio + "/wsinventario.asmx";

        String SOAP_ACTION = "http://tempuri.org/GetInventario";

        String NAMESPACE = "http://tempuri.org/";

        SoapObject response = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GetInventario");
        // Property which holds input parameters
        PropertyInfo auto = new PropertyInfo();
        // Set Username
        auto.setName("autorizacao_");
        // Set Value
        auto.setValue(autorizaocao);
        // Set dataType
        auto.setType(String.class);
        // Add the property to request object
        request.addProperty(auto);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        DashBoardModelo d = new DashBoardModelo();
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION, envelope);
            // Get the response
            response = (SoapObject) envelope.getResponse();

            if (response == null) {
                return 0;
            } else {
                d.setIdInventario(Integer.parseInt(response.getProperty("IdInventario").toString()));
            }


        } catch (Exception e) {
            //Assign Error Status true in static variable 'errored'
            ERROR = e.getMessage();
            e.printStackTrace();
        }
        //Return booleam to calling object
        return d.getIdInventario();
    }

    public static List<DashBoardModelo> GetDashBoard(int idInventario) {

        //String URL = "http://179.184.159.52/invtablet/wsinventario.asmx";
        String URL = "http://" + servidor + "/" + diretorio + "/wsinventario.asmx";

        String SOAP_ACTION = "http://tempuri.org/GetDashboard";

        String NAMESPACE = "http://tempuri.org/";

        SoapObject response;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, "GetDashboard");
        // Property which holds input parameters
        PropertyInfo auto = new PropertyInfo();
        // Set Username
        auto.setName("idInventario");
        // Set Value
        auto.setValue(idInventario);
        // Set dataType
        auto.setType(String.class);
        // Add the property to request object
        request.addProperty(auto);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        List<DashBoardModelo> lista = new ArrayList<>();
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION, envelope);
            // Get the response
            response = (SoapObject) envelope.getResponse();

            SoapObject[] array;
            if (response == null) {
                return null;
            } else {
                //pegando o array do serviço
                array = new SoapObject[response.getPropertyCount()];
                for (int i = 0; i < array.length; i++) {

                    array[i] = (SoapObject) response.getProperty(i);
                }

                //pegando cada variavel do array
                for (int j = 0; j < array.length; j++) {
                    DashBoardModelo d = new DashBoardModelo();
                    d.setNomeLoja(array[j].getProperty("nomeLoja").toString());
                    d.setData(array[j].getProperty("data").toString());
                    d.setAutorizao(Long.parseLong(array[j].getProperty("autorizacao").toString()));
                    d.setHorarioInicioEnderecamento(array[j].getProperty("horarioInicioEnderecamento").toString());
                    d.setHorarioInicioContagem(array[j].getProperty("horarioInicioContagem").toString());
                    d.setTotalDeEnderecos(Long.parseLong(array[j].getProperty("totalDeEnderecos").toString()));
                    d.setTotalDeDiasEstimados(Long.parseLong(array[j].getProperty("totalDeDiasEstimados").toString()));
                    d.setTotalColaboradores(Long.parseLong(array[j].getProperty("totalColaboradores").toString()));
                    d.setBandeira(array[j].getProperty("bandeira").toString());
                    d.setFilial(array[j].getProperty("filial").toString());
                    d.setPrevisaoPecas(Long.parseLong(array[j].getProperty("previsaoPecas").toString()));
                    d.setTotalPecasRealizado(Long.parseLong(array[j].getProperty("totalPecasRealizado").toString()));
                    d.setPrevisaoEnderecos(Long.parseLong(array[j].getProperty("previsaoEnderecos").toString()));
                    if (!array[j].getProperty("horarioInicioAuditoria").toString().equals("anyType{}")) {
                        d.setHorarioInicioAuditoria(array[j].getProperty("horarioInicioAuditoria").toString());
                    } else {
                        d.setHorarioInicioAuditoria("Não ocorreu auditoria");
                    }

                    if (!array[j].getProperty("horarioFimAuditoria").toString().equals("anyType{}")) {
                        d.setHorarioFimAuditoria(array[j].getProperty("horarioFimAuditoria").toString());
                    } else {
                        d.setHorarioFimAuditoria("Não ocorreu auditoria");
                    }

                    if (!array[j].getProperty("horarioInicioDivergencia").toString().equals("anyType{}")) {
                        d.setHorarioInicioDivergencia(array[j].getProperty("horarioInicioDivergencia").toString());
                    } else {
                        d.setHorarioInicioDivergencia("Não ocorreu divergência");
                    }

                    if (!array[j].getProperty("horarioFimDivergencia").toString().equals("anyType{}")) {
                        d.setHorarioFimDivergencia(array[j].getProperty("horarioFimDivergencia").toString());
                    } else {
                        d.setHorarioFimDivergencia("Não ocorreu divergência");
                    }

                    if (Long.parseLong(array[j].getProperty("qtdeSku").toString()) < 0) {
                        d.setQtdeSku(0);
                    } else {
                        d.setQtdeSku(Long.parseLong(array[j].getProperty("qtdeSku").toString()));
                    }
                    d.setNumeroPaginas(Long.parseLong(array[j].getProperty("numeroPaginas").toString()));
                    d.setQtdeAlteracao(Integer.parseInt(array[j].getProperty("qtdeAlteracao").toString()));
                    d.setTotalEnderecoDpto(Long.parseLong(array[j].getProperty("totalEnderecoDpto").toString()));
                    d.setContEnderecoDpto(Long.parseLong(array[j].getProperty("contEnderecoDpto").toString()));
                    d.setPorcentEnderecoDpto(Integer.parseInt(array[j].getProperty("porcentEnderecoDpto").toString()));

                    lista.add(d);
                }
            }


        } catch (Exception e) {
            //Assign Error Status true in static variable 'errored'
            ERROR = e.getMessage();
            e.printStackTrace();
        }
        //Return booleam to calling object
        return lista;
    }
}
