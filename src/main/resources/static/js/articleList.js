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
            coverImage: $('#coverImage').val()
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
        $('#save-btn').text('保存');
        $('html,body').animate({scrollTop: 0}, 200);
    });

    // 重置
    $(document).on('click', '.reset', function () {
        resetForm();
    });

    // 爬虫功能
    $(document).on('click', '#crawl-btn', function () {
        var startPage = parseInt($('#startPage').val()) || 1;
        var endPage = parseInt($('#endPage').val()) || 5;
        
        // 验证输入
        if (startPage < 1 || startPage > 50) {
            alert('开始页数必须在1-50之间');
            return;
        }
        if (endPage < 1 || endPage > 50) {
            alert('结束页数必须在1-50之间');
            return;
        }
        if (startPage > endPage) {
            alert('开始页数不能大于结束页数');
            return;
        }
        
        // 显示加载状态
        $('#crawl-btn').prop('disabled', true);
        $('#crawl-text').text('爬取中...');
        $('#crawler-form').addClass('loading');
        
        // 发送爬虫请求
        $.ajax({
            url: '/article/crawl',
            type: 'GET',
            data: {
                startPage: startPage,
                endPage: endPage
            },
            success: function (response) {
                // 显示结果
                $('#crawl-result').show();
                
                // 设置状态
                var statusClass = response.success ? 'success' : 'error';
                var statusText = response.success ? '成功' : '失败';
                $('#result-status').text(statusText).removeClass('success error').addClass(statusClass);
                
                // 设置数值
                $('#result-new-news').text(response.newNews || 0);
                $('#result-existing-news').text(response.existingNews || 0);
                $('#result-message').text(response.message || '');
                
                // 如果成功，显示成功消息并刷新页面
                if (response.success) {
                    setTimeout(function() {
                        if (confirm('爬取成功！是否刷新页面查看最新文章？')) {
                            location.reload();
                        }
                    }, 1000);
                }
            },
            error: function (xhr, status, error) {
                // 显示错误结果
                $('#crawl-result').show();
                $('#result-status').text('失败').removeClass('success error').addClass('error');
                $('#result-new-news').text('0');
                $('#result-existing-news').text('0');
                $('#result-message').text('网络错误或服务器异常');
                console.error('爬虫请求失败:', error);
            },
            complete: function () {
                // 恢复按钮状态
                $('#crawl-btn').prop('disabled', false);
                $('#crawl-text').text('开始爬取新闻');
                $('#crawler-form').removeClass('loading');
            }
        });
    });
    
    // 页面加载时隐藏结果区域
    $('#crawl-result').hide();
});