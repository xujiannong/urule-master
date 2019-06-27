/**
 * @author GJ
 */
import {MsgBox} from 'flowdesigner';

urule.ImportExcelDialog=function(parent){
	this.parent=parent;
	this.init();
};

urule.ImportExcelDialog.prototype.open=function(){
	const _this=this;
	MsgBox.showDialog('导入Excel',this.dialogContent,[
		{
			name:'上传',
			holdDialog:true,
			click:function(){
                var file=$('[name=file]').val();
                if(!file || file.length<2){
                    bootbox.alert('请选择要导入的文件');
                    return;
                }
                document.getElementById(formId).submit();
			}
		}
	]);
};

urule.ImportExcelDialog.prototype.init=function(){
	var self=this;
    var url = window._server + "/decisiontableeditor/importExcel?project=" + encodeURI(window._project);
    const table = $('<form enctype="multipart/form-data" style="height: 70px;" method="post" target="frame_for_import" action="' + url + '"></form>');
	this.tbody=$(`<tbody></tbody>`);
	table.append(this.tbody);
    var s = $('<div class="form-group"><label>请选择要导入的Excel文件：</label></div>');
    a.append(s);
    var c = $('<input type="file" name="excel_file" style="display: inline-block">');
    s.append(c);
    var u = $('<input type="submit" value="上传" class="btn btn-default" style="float: right">');
    a.append(u);
    var l = $('<iframe name="frame_for_import" style="width: 0;height: 0;border: 0px"></iframe>');
    table.append(l),
        l.load(function() {
            var t = $(this).contents().find("body").text();
            if (t && !(t.length < 5)) {
                var e = JSON.parse(t);
                if (e.fail) bootbox.alert("Excel导入失败：<span style='color: #d30e00;'>" + e.msg + "</span>");
                else {
                    var n = (0, i.getParameter)("file"),
                        r = window._server + "/decisiontableeditor?file=" + n + "&doImport=true";
                    window.open(r, "_self")
                }
            }
        })
	this.dialogContent=$('<div>');
	this.dialogContent.append(table);

};
