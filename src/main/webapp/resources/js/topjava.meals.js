const mealAjaxUrl="ajax/profile/meals/";
function updateTableByFilter(){
    $.ajax({
        type:"GET",
        url: mealAjaxUrl+"filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function cancelFilter(){
    $("#filter")[0].reset();
    $.get(mealAjaxUrl,updateTableByData);
}

$(function(){
    makeEditable({
    ajaxUrl: mealAjaxUrl,
    datatableApi: $("#datatable").DataTable({
        "ajax":{
            "url": mealAjaxUrl,
            "dataSrc": ""
        },
        "paging":false,
        "info":true,
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
                "render": renderEditBtn,
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "render": renderDeleteBtn,
                "defaultContent": "Delete",
                "orderable": false

            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function(row,data,dataIndex){
            $(row).attr("data-mealExcess",data.excess);
        }

    })
        ,
        updateTable: updateTableByFilter
});
    const startDate=$('#startDate');
    const endDate=$('#endDate');
    startDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function(opt){
            this.setOptions({
                maxDate: endDate.val() ? endDate.val() : false
                })
        }
    });
    endDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function(opt){
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
        formatDate: 'H:i',
        onShow: function(opt){
            this.setOptions({
                maxTime: endTime.val() ? endTime.val() : false
            })
        }
    });
    endTime.datetimepicker({
        datepicker: false,
        format: 'H:i',
        formatDate: 'H:i',
        onShow: function(opt){
            this.setOptions({
                minTime: startTime.val() ? startTime.val() : false
            })
        }
    });
    $("#dateTime").datetimepicker({
        format: 'Y-m-d H:i'
    })

});