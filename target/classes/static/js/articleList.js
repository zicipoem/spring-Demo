$(function () {
    function resetForm() {
        $('#article-form')[0].reset();
        $('#id').val('');
        $('#save-btn').text('添加');
    }

    // 新增或保存（后端 save 支持有 id 时更新）
    $('.add').on('click', function () {
        var data = {
            id: $('#id').val(),
            title: $('#title').val(),
            summary: $('#summary').val(),
            content: $('#content').val(),
            coverImage: $('#coverImage').val(),
            images: $('#images').val().split('\n').filter(url => url.trim() !== '')
        };
        $.post('/article/add/model', data, function (res) {
            // 直接刷新页面以显示最新列表
            location.reload();
        }).fail(function () {
            alert('操作失败');
        });
    });

    // 删除
    $(document).on('click', '.del', function () {
        if (!confirm('确定要删除吗？')) {
            return;
        }
        var id = $(this).data('id');
        $.get('/article/delete/path/' + id, function () {
            location.reload();
        }).fail(function () {
            alert('删除失败');
        });
    });

    // 编辑：从行数据填充表单
    $(document).on('click', '.edit', function () {
        var tr = $(this).closest('tr');
        $('#id').val(tr.data('id'));
        $('#title').val(tr.data('title'));
        $('#summary').val(tr.data('summary'));
        $('#content').val(tr.data('content'));
        $('#coverImage').val(tr.data('coverimage'));
        $('#images').val(tr.data('images').replace(/,/g, '\n'));
        $('#save-btn').text('保存');
        $('html,body').animate({scrollTop: 0}, 200);
    });

    // 重置
    $(document).on('click', '.reset', function () {
        resetForm();
    });
});