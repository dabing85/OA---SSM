<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="top.jsp"/>

<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 编辑员工 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <%--不使用form表单，这里使用spring mvc里的form表单--%>
                <%--<form id="admin-form" name="addForm" action="/department/add" method="post">--%>
                    <form:form action="/employee/update" modelAttribute="employee" id="admin-form" name="addForm">
                        <%--modelAttribute="department"这里的department这个名字要和控制器里的toAdd那里设置的map里的名称一样--%>
                        <form:hidden path="password"/><%--密码隐藏提交--%>
                        <div class="panel-body bg-light">
                            <div class="section-divider mt20 mb40">
                                <span> 编辑员工信息 </span>
                            </div>
                            <div class="section row">
                                <div class="col-md-6">
                                    <label for="sn" class="field prepend-icon">
                                        <form:input path="sn" cssClass="gui-input" placeholder="工号..." readonly="true"/>
                                        <label for="sn" class="field-icon">
                                            <i class="fa fa-user"></i>
                                        </label>
                                    </label>
                                </div>
                                <div class="col-md-6">
                                    <label for="name" class="field prepend-icon">
                                        <form:input path="name" class="gui-input" placeholder="姓名..."/>
                                        <label for="name" class="field-icon">
                                            <i class="fa fa-user"></i>
                                        </label>
                                    </label>
                                </div>
                            </div>
                            <div class="section row">
                                <div class="col-md-6">
                                    <label for="sn" class="field select">
                                        <form:select path="departmentSn" items="${dlist}" itemLabel="name" itemValue="sn" cssClass="gui-input" placeholder="所属部门..."/>
                                        <i class="arrow double"></i>
                                    </label>
                                </div>
                                <div class="col-md-6">
                                    <label for="post" class="field select">
                                        <form:select path="post" items="${plist}" cssClass="gui-input" placeholder="职务..."/>
                                        <i class="arrow double"></i>
                                    </label>
                                </div>
                            </div>
                            <div class="panel-footer text-right">
                                <button type="submit" class="button"> 保存 </button>
                                <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>
                            </div>
                        </div>
                </form:form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="bottom.jsp"/>