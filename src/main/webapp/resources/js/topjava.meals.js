// $(document).ready(function () {

function filter(){
  $.ajax({
     type: "GET",
     url: "ajax/profile/meals/filter",
     data: $("#filterForm").serialize()
  }).done(updateDataForTable);
}

function cancelFilter(){
    $("#filterForm")[0].reset();
    $.get("ajax/profile/meals",updateDataForTable);

}
$(function () {
    makeEditable({
            ajaxUrl: "ajax/profile/meals/",
            datatableApi: $("#tableMeals").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
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
                        "desc"
                    ]
                ]
            }),
        updateTable: filter
        }
    );
});
