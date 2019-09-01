var ajaxUrlUsers = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    makeEditable({

    ajaxUrl : ajaxUrlUsers,
    datatableApi : $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    }),
        updateTable: function(){
            $.get(ajaxUrlUsers,updateTableByData);
        }
        }
    );
});