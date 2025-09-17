<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>List Categories</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <style>
        .header-section { background-color: #343a40; color: white; padding: 10px 20px; margin-bottom: 20px; }
        .table thead th { background-color: #f8f9fa; }
        .table td, .table th { vertical-align: middle; }
        .action-buttons a { margin: 0 5px; }
        .footer-section { background-color: #f8f9fa; padding: 10px 20px; text-align: center; margin-top: 20px; }
    </style>
</head>
<body>
    <div class="header-section">
        <h4><i class="fas fa-list"></i> List Categories</h4>
    </div>

    <section class="row">
        <div class="col mt-4">
            <div class="card">
                <div class="card-header">
                    <h4>List Categories</h4>
                    <form action="${pageContext.request.contextPath}/admin/categories/search" method="get" class="form-inline">
                        <div class="form-group mr-2">
                            <input type="text" name="name" class="form-control" placeholder="Search by name..." value="${searchName}">
                        </div>
                        <button type="submit" class="btn btn-outline-primary"><i class="fas fa-search"></i> Search</button>
                        <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-outline-secondary ml-2"><i class="fas fa-times"></i> Clear</a>
                    </form>
                    <a href="${pageContext.request.contextPath}/admin/categories/add" class="btn btn-success float-right"><i class="fas fa-plus"></i> Add New Category</a>
                </div>
                <div class="card-body">
                    <c:if test="${message != null}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <strong>Success!</strong> ${message}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    </c:if>
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>Category ID</th>
                                <th>Category Name</th>
                                <th>Images</th>
                              
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty categories}">
                                    <tr>
                                        <td colspan="4" class="text-center">No categories found.</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${categories}" var="category">
                                        <tr>
                                            <td>${category.categoryID}</td>
                                            <td>${category.categoryName}</td>
                                            <td>${category.images}</td>
                                            <td class="action-buttons">
                                                <a href="${pageContext.request.contextPath}/admin/categories/view/${category.categoryID}" class="btn btn-sm btn-outline-info"><i class="fa fa-info-circle"></i></a>
                                                <a href="${pageContext.request.contextPath}/admin/categories/edit/${category.categoryID}" class="btn btn-sm btn-outline-warning"><i class="fa fa-edit"></i></a>
                                                <a href="${pageContext.request.contextPath}/admin/categories/delete/${category.categoryID}" class="btn btn-sm btn-outline-danger" onclick="return confirm('Are you sure?');"><i class="fa fa-trash"></i></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
                <div class="card-footer text-muted">
                    <!-- Phân trang bị loại bỏ vì dùng findAll -->
                </div>
            </div>
        </div>
    </section>

    <div class="footer-section">
        <p>&copy; 2025 Your Application. All rights reserved.</p>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>