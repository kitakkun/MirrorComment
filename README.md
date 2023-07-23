# MirrorComment

非公式のミラティブ配信コメントビューアです．Windows向けのものはあったものの，macOS版がなかったので作りました．

## 対応プラットフォーム

macOS, Windows, Linux全てで動作する（？）はずです．
動かなかったらIssue立てて報告してくれると嬉しいです．

現時点で動作確認は macOS でのみ行っています．

## 仕組み
裏でChromeブラウザを立ち上げ，HTML要素を監視することで新規コメントを取得しています．
読み上げたコメントは，VOICEVOXエンジンによって音声に変換され再生されます．

## 必要なもの

音声読み上げにはVOICEVOXエンジンが必要です．
VOICEVOXアプリを立ち上げた状態で使用してください．

設定画面で適切なURLを指定する必要がありますが，通常のインストールでは
`http://127.0.0.1:50021` と入力しておけば問題ないはずです．

## リリースの予定

現時点ではまだ開発途中なので，リリースはされていません．
詳しい説明は省きますが，本リポジトリをクローンしてJDK11以上の環境で `./gradlew run` すれば動かせると思います．

## 免責事項

本ソフトウェアを使用したことによって生じたいかなる損害についても，作者は一切の責任を負いません．
使用に関しては自己責任でお願いいたします．
