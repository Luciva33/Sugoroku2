import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GamePlay {
	Random rand = new Random();

	//メンバ変数を追加
	
	
	
	

	private List<String> squareContentList = new ArrayList<String>() {
		{
			add("なし。");
			add("少し眠たくなった。1回休み。");
			add("体が軽い。2マス進む。");
			add("なし。");
			add("なし。");
			add("後ろ歩きしてみた。1マス戻る。");
			add("なし。");
			add("レンタカーを借りた。次は2倍進む。");
			add("財布をおとした！2マス戻る。");
			add("なし。");
			add("なし。");
			add("道案内をたのまれた。1マス戻る。");
			add("病気になった...2回休み。");
			add("ゴールへの近道をする！。");
			add("用事を思い出した。3マス戻る。");
		}
	};

	private List<Integer> addBreakNumList = new ArrayList<Integer>() {
		{

			add(0); // 1マス目：なし
			add(1); // 2マス目：1回休み
			add(0); // 3マス目：2マス進む
			add(0); // 4マス目：なし
			add(0); // 5マス目：なし
			add(0); // 6マス目：1マス戻る
			add(0); // 7マス目：なし
			add(0); // 8マス目：次は2倍進む
			add(0); // 9マス目: ２マス戻る1
			add(0); // 10マス目：なし
			add(0); // 11マス目：なし
			add(0); // 12マス目: 1マス戻る
			add(2); // 13マス目：1回休み
			add(0); // 14マス目：ゴールに到着する
			add(0); // 15マス目：3マス戻る	
		}
	};

	public List<Integer> addPosNumList = new ArrayList<Integer>() {
		{

			add(0); // なし
			add(0); // 1回休み
			add(2); // 2マス進む
			add(0); // なし
			add(0); // なし
			add(-1); // 1マス戻る
			add(0); // なし
			add(0); // 次は2倍進む
			add(-2);//	2マスもどる
			add(0); // なし
			add(0); // なし
			add(0); // なし
			add(-1); // 1マス戻る
			add(99); // ゴールに到着する
			add(-3); // 3マス戻る
		}
	};

	private List<Integer> moveMultipleList = new ArrayList<Integer>() {
		{

			add(1); // なし
			add(1); // 1回休み
			add(1); // 2マス進む
			add(1); // なし
			add(1); // なし
			add(1); // 1マス戻る
			add(1); // なし
			add(2); // 次は2倍進む
			add(1); // ２マス戻る
			add(1); // なし
			add(1); // なし
			add(1); // 1マス戻る
			add(1); // 2回休み
			add(1); // ゴールに到着する
			add(1); // 3マス戻る

		}

	};

	/*	スタートとゴールまでのマップのマス数
	*/
	private int squareNum = this.squareContentList.size();
	//private int squereMapNum = rand.nextInt(30) + 20;
	//private int squareNum = squereMapNum;

	public void startUp() {
		//ゲーム起動

		System.out.println("Game startUp start");

		//ゲームループ

		boolean isGameContinue = true; //ゲーム継続フラグ
		while (isGameContinue) {

			//ゲーム開始選択

			int selectedGameStart = 0;
			// ゲーム開始選択値(1:開始/1以外:終了)
			selectedGameStart = this.selectGameStart();
			try {
				if (selectedGameStart != 1) {

					//ゲーム終了

					this.printGameFinish();//ゲーム終了メソッドの呼び出し

					isGameContinue = false; //ゲーム継続フラグ(false)

				}
				//ゲーム開始(1)が入力された場合
				else {

					//ゲームプレイ処理

					//ゴールに到達した場合はfalse(終了)、途中終了した場合はtrue(継続)
					isGameContinue = this.playGame();
				}

			} catch (Exception e) {
				;
			}

		}

		//ゲー開始終了分岐

		//ゲーム終了
		System.out.println("Game startUp end");

	}

	//playメソッド

	public boolean playGame() {

		boolean isGameContinue = true; // ゲーム継続フラグ(true:継続／false:終了)

		//移動マス計算　プレイヤー情報

		int currentPosition = 0; //現在位置のマス数(スタート地点を0とする)
		int breakNum = 0; //行動休み回数
		int moveMultiple = 1; // 移動倍率 
		int count = 0; //ターン数

		//マップの初期表示

		this.printMap2(this.squareContentList, currentPosition);

		//行動ループ

		boolean isActionContinue = true; //行動継続フラグ(true:継続／false:終了)

		while (isActionContinue) {
			//行動継続フラグ　
			//ループ条件 　isActionContinueの値がfalseになるまで
			//ゴールに到達したとき、行動継続フラグにfalseを代入することでループから抜ける

			//移動、終了選択

			int selectedMoveOrStop = 0; // 移動終了選択値(1:移動／1以外:途中終了)
			selectedMoveOrStop = this.selectMoveOrStop(currentPosition,count);

			// 移動途中終了分岐

			if (selectedMoveOrStop != 1) {
				break; //行動ループから抜ける // ここでロードファイルできるようにしたい	

			} else {

				//休み処理

				if (breakNum > 0) {
					//休みメッセージ出力
					this.printBreakTime();

					breakNum -= 1; //休み回数から一回分引く
					count++;
					continue; //次のループへ

				}

				//移動する

				//移動マス数を計算

				int moveValue = this.calculateMoveValue(moveMultiple);

				//移動後の現在位置マス数を計算
				currentPosition += moveValue;

				//マップを書く　移動後

				this.printMap2(this.squareContentList, currentPosition);

				//イベント発生1
				
				this.printEvent(this.squareContentList, currentPosition);

				//現在位置マス数がゴールまでのマス数より大きい場合は
				//ゴールしていると想定されるためイベント発生の処理をしない

				if (currentPosition <= this.squareNum) {

					//発生したイベント内容を出力

					//this.printEvent(this.squareContentList, currentPosition);

					//イベント効果を反映

					//現在位置マス-1がリストのindexと対応1

					int listIndex = currentPosition - 1;

					//行動休み回数
					breakNum = this.addBreakNumList.get(listIndex);

					//現在位置にイベント効果のマス数を加算
					currentPosition += this.addPosNumList.get(listIndex);

					//移動倍率

					moveMultiple = this.moveMultipleList.get(listIndex);
				
					count++;
					
				}

				//ゴール到達分岐

				//ゴールに到達した場合
				if (currentPosition > this.squareNum) {
					//プレイヤーの位置 > マス

					this.printGameClear(count);

					isActionContinue = false; // 行動継続フラグ(false:終了)
					isGameContinue = false; // ゲーム継続フラグ(false:終了)

				}

				//ゴールに到達していない場合
				else {

					//マップを書く イベント発生後

					this.printMap2(this.squareContentList, currentPosition);
				
				}

			}

		}
		return isGameContinue;

	}

	public int selectGameStart() {
		//選択要求メッセージ
		String requestMsg = "";
		requestMsg = ""

				+ "\n"
				+ "\n" + "****************************"
				+ "\n" + "*"
				+ "\n" + "* すごろくゲーム"
				+ "\n" + "*"
				+ "\n" + "****************************"
				+ "\n" + "★ キーボードから以下の数値を入力してください。"
				+ "\n" + "  ［1］：ゲームを開始する。"
				+ "\n" + "  ［1以外］：ゲームを終了する。"
				+ "\n";

		System.out.println(requestMsg);

		//コンソールから入力値を受け取る
		Scanner sc = new Scanner(System.in);
		int inputNo = sc.nextInt();

		return inputNo;
	}

	//ゲーム終了メソッド
	public void printGameFinish() {
		//ゲーム終了メッセージ

		String printMsg = "";
		printMsg = ""
				+ "\n"
				+ "\n" + "ゲームを終了しました。"
				+ "\n";

		System.out.println(printMsg);

	}

	/*マップを書く
	 スタートからゴールまでのマスを○で出力
	 現在位置は●で出力
	 各マスにイベント内容を出力
	*/

	
	private void printMap(List<String> contentList, int currentPos) {
	//スタートを出力

	System.out.println("スタート" + "\n" + "|");

	//スタートからゴールまでのマスとイベント内容を出力する
	for (int i = 0; i <  contentList.size(); i++) {
		int loopCnt = i + 1; //ループ回数目
		String printSquare = ""; //1マス出力する内容

		//マスの出力内容を分岐する

		String squareKind = "〇"; //デフォルトのマスは〇で出力

		//ループ回数が現在位置のマス目と同じか？

		if (loopCnt == currentPos) {
			squareKind += "●"; //現在位置のマスは●で出力する

		}

		//イベント内容の出力
		//イベント内容をリストから取得

		String eventContent = contentList.get(i);

		//各マスに出力する内容を生成
		// 〇　イベント内容
		
		
		printSquare += squareKind + "    " + eventContent;
		printSquare += "\n" + "|";

		System.out.println(printSquare);
		
	}

	//ゴールの出力
	System.out.println("...");

}
	

	public int selectMoveOrStop(int c, int t) {
		//選択要求メッセージ
		String requestMsg = "";
		requestMsg = ""
				+ "\n"
				+ "\n" + "★ キーボードから以下の数値を入力してください。"
				+ "\n" + "残りマス数: " + (squareNum - c)
				+ "\n" + "ターン数: "+ t
				+ "\n" + "  ［1］：移動する。"
				+ "\n" + "  ［1以外］：ゲームを途中で終了して、開始画面に戻る。"
				+ "\n";

		System.out.println(requestMsg);

		//コンソールから入力を受け取る
		Scanner sc = new Scanner(System.in);
		int inputNo = sc.nextInt();
		return inputNo;

	}

	//移動するマス数を計算する.
	//returnで移動するマス数を返す

	private int calculateMoveValue(int moveMultiple) {

		//通常の移動マス数を取得
		//1~3の範囲でランダムな数値を取得する

		int val = this.getRandomValue(3);

		//イベントの効果を移動力に反映

		//移動倍率をかける

		val = val * moveMultiple;

		return val;
	}

	/*ランダムな数値を取得する
	  param max ランダム値の最大値
	  return ランダム値	
	*/
	private int getRandomValue(int max) {
		Random rand = new Random();
		int val = rand.nextInt(max) + 1;
		return val;
	}

	public void printGameClear(int t) {
		//ゲームクリアメッセージ
		String printMsg = ""
				+ "\n"
				+ "\n" + "★ ★ ★ ★ ★ ★ ★ ★ ★ ★ ★ ★ ★ ★"
				+ "\n" + ""
				+ "\n" + " おめでとうございます！"
				+ "\n" + " ゲームをクリアしました！！"
				+ "\n" + ""
				+ "\n" + " ターン数: " + t 
				+ "\n" + ""
				+ "\n" + "★ ★ ★ ★ ★ ★ ★ ★ ★ ★ ★ ★ ★ ★"
				+ "\n";

		System.out.println(printMsg);
	}

	private void printBreakTime() {
		//休みメッセージ

		String printMsg = "";
		printMsg = ""
				+ "\n"
				+ "\n" + "------------------------------"
				+ "\n" + " 休み中。"
				+ "\n" + " 今回は何もしない。            "
				+ "\n" + "------------------------------"
				+ "\n";
		System.out.println(printMsg);

	}

	//イベント発生出力の関数
	//contentList イベント内容リスト
	//currentPos プレイヤーの現在位置

	private void printEvent(List<String> contentList, int currentPos) {
		String printEvent = ""; //出力するイベント

		//現在位置マスのイベント内容をリストから取得
		// リストのindexは0始まりのため現在位置マス-1
		String eventContent = contentList.get(currentPos - 1);

		//出力用の形式に整える

		printEvent = ""
				+ "\n"
				+ "\n" + "------------------------------"
				+ "\n" + "★ ★ ★ イベントが発生 ★ ★ ★"
				+ "\n" + " " + eventContent
				+ "\n" + "------------------------------"
				+ "\n";

		System.out.println(printEvent);
	}
	
	private void printMap2(List<String> contentList, int currentPos) {
	//スタートを出力

	System.out.println("現在地" + "\n" + "|");

	//スタートからゴールまでのマスとイベント内容を出力する
	
	//if(currentPos +3 <  contentList.size() ) {
		int start = currentPos - 3 <=0?0:currentPos-3; 
		int last = currentPos + 3 >= contentList.size()?contentList.size():currentPos+3; 
		for (int i = start ; i <  last ; i++) {
			int loopCnt = i + 1; //ループ回数目
			String printSquare = ""; //1マス出力する内容
	
			//マスの出力内容を分岐する
	
			String squareKind = "〇"; //デフォルトのマスは〇で出力
	
			//ループ回数が現在位置のマス目と同じか？
	
			if (loopCnt == currentPos) {
				squareKind += "●"; //現在位置のマスは●で出力する
			
			}
		
			//イベント内容の出力
			//イベント内容をリストから取得
	
			String eventContent = contentList.get(i);
	
			//各マスに出力する内容を生成
			// 〇　イベント内容
			
			
			printSquare += squareKind + "    " + eventContent;
			printSquare += "\n" + "|";
	
			System.out.println(printSquare);
		}		
			
	//}else{
		
		
	//}

	//ゴールの出力
	System.out.println("...");

}
	
	
	
	

}
