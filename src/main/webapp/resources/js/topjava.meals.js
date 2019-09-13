function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: "ajax/profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "ajax": {
               "url":     "ajax/profile/meals/",
               "dataSrc": ""
          },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function(date,type,row){
                        if(type==="display"){
                            return formatDate(date);
                        }
                        return date;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function(row,data,dataIndex){
                $(row).attr("data-mealExcess",data.excess)
            }
        }),
        updateTable: updateFilteredTable
    });
    const startDate = $('#startDate');
        const endDate = $('#endDate');
    startDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
           formatDate: 'Y-m-d',
           onShow: function (opt) {
               this.setOptions({
                       maxDate: endDate.val() ? endDate.val() : false
                   })
           }
  });

    endDate.datetimepicker({
      timepicker: false,
      format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function (opt) {
            this.setOptions({
                minDate: startDate.val() ? startDate.val() : false
            })
        }
  });
    const startTime=$('#startTime');
    const endTime=$('#endTime');
    startTime.datetimepicker({
      datepicker: false,
      format: 'H:i',
      onShow: function(opt) {
          this.setOptions({
              maxTime: endTime.val() ? endTime.val() : false
          })
      }
  });

    endTime.datetimepicker({
      datepicker: false,
      format: 'H:i',
      onShow: function(opt) {
          this.setOptions({
              minTime: startTime.val() ? startTime.val() : false
          })
      }
  });
  $("#dateTime").datetimepicker({
      format: 'Y-m-d H:i'
  });
});