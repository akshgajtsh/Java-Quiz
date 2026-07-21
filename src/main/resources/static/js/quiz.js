async function uploadCsvApi() {
	    const fileInput = document.getElementById('csvFile');
	    const msgDiv = document.getElementById('apiMessage');

	    if (!fileInput.files || fileInput.files.length === 0) {
	        alert("ファイルを選択してください。");
	        return;
	    }

	    // フォームデータの作成
	    const formData = new FormData();
	    formData.append('file', fileInput.files[0]);

	    try {
	        // APIヘPOSTリクエストを送信
	        const response = await fetch('/api/quiz/upload-csv', {
	            method: 'POST',
	            body: formData
	        });

	        const result = await response.json();

	        // メッセージ表示エリアの初期化
	        msgDiv.classList.remove('d-none', 'alert-success', 'alert-danger');

	        if (result.success) {
	            msgDiv.classList.add('alert', 'alert-success');
	            msgDiv.innerText = result.message;
	            fileInput.value = ''; // ファイル選択をクリア
	            
	            // 2秒後に画面を更新して追加されたクイズ一覧を反映（必要に応じて）
	            setTimeout(() => location.reload(), 1500);
	        } else {
	            msgDiv.classList.add('alert', 'alert-danger');
	            msgDiv.innerText = result.message;
	        }
	    } catch (error) {
	        console.error('Error:', error);
	        msgDiv.classList.remove('d-none');
	        msgDiv.classList.add('alert', 'alert-danger');
	        msgDiv.innerText = '通信エラーが発生しました。';
	    }
	}