$(function () {
    function resetForm() {
        $('#user-form')[0].reset();
        $('#id').val('');
        $('#save-btn').text('添加');
    }

    // 新增或保存
    $('.add').on('click', function () {
        var data = {
            id: $('#id').val(),
            name: $('#name').val(),
            password: $('#password').val(),
            avatar: $('#avatar').val()
        };
        $.post('/user/add/model', data, function (res) {
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
        $.get('/user/delete/path/' + id, function () {
            location.reload();
        }).fail(function () {
            alert('删除失败');
        });
    });

    // 编辑
    $(document).on('click', '.edit', function () {
        var tr = $(this).closest('tr');
        $('#id').val(tr.data('id'));
        $('#name').val(tr.data('username'));
        $('#avatar').val(tr.data('avatar'));
        $('#password').val('password');
        $('#save-btn').text('保存');
        $('html,body').animate({scrollTop: 0}, 200);
    });

    // 重置
    $(document).on('click', '.reset', function () {
        resetForm();
    });
});