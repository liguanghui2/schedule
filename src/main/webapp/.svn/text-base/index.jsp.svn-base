<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.net.InetAddress" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>front-busystock</title>
    <link rel="shortcut icon" href="/favicon.ico"/>
</head>
<body>
<%
        try {
            java.lang.String hostName = InetAddress.getLocalHost().getHostName().toString();
            String systemTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            out.println("<h2>welcome to gps-schedule </h2>");
            out.println("<h4>hostName:</h4><h1>&nbsp;&nbsp&nbsp;&nbsp;" + hostName + "</h1>");
            out.println("<h4>machineTime:</h4><h1>&nbsp;&nbsp&nbsp;&nbsp;" + systemTime + "</h1>");
        } catch (Exception ex) {
            ex.printStackTrace();
            out.println("ERROR");
        }
%>
</body>
</html>