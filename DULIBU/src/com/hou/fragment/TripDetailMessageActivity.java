package com.hou.fragment;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.emitter.Emitter.Listener;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

import com.hou.Messages.Message;
import com.hou.Messages.MessageAdapter;
import com.hou.app.Global;
import com.hou.dulibu.R;
import com.hou.dulibu.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TripDetailMessageActivity extends Fragment {

	private static final int REQUEST_LOGIN = 0;

	private static final int TYPING_TIMER_LENGTH = 600;

	private RecyclerView mMessagesView;
	private EditText mInputMessageView;
	private List<Message> mMessages = new ArrayList<Message>();
	private RecyclerView.Adapter mAdapter;
	private boolean mTyping = false;
	private Handler mTypingHandler = new Handler();
	private String mUsername;
	private Socket mSocket;
	{
		try {
			mSocket = IO.socket(Global.BASE_URI);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public TripDetailMessageActivity() {
		super();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mAdapter = new MessageAdapter(activity, mMessages);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
		mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
		mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
		mSocket.on("new_message", onNewMessage);
//		mSocket.on("user joined", onUserJoined);
//		mSocket.on("user left", onUserLeft);
		mSocket.on("typing", onTyping);
		mSocket.on("stop typing", onStopTyping);
		mSocket.on(".join", onJoin);
		mSocket.on(".error", ERROR);
		mSocket.connect();
		
		JSONObject data = new JSONObject();
		try {
			data.put("access_token", Global.getPreference(getActivity(), Global.ACCESS_TOKEN, "NONE"));
			data.put("target_type", Global.TARGET_TRIP);
			data.put("target_id", "5623e80833a5eff517534e74");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mSocket.emit(".join", data);
		
		mUsername = Global.getPreference(getActivity(), Global.USER_FULLNAME, "UNKNOWN");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.trip_detail_message, container, false);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		mSocket.disconnect();
		mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
		mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
		mSocket.off("new message", onNewMessage);
		mSocket.off("user joined", onUserJoined);
		mSocket.off("user left", onUserLeft);
		mSocket.off("typing", onTyping);
		mSocket.off("stop typing", onStopTyping);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mMessagesView = (RecyclerView) view.findViewById(R.id.messages);
		mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mMessagesView.setAdapter(mAdapter);

		mInputMessageView = (EditText) view.findViewById(R.id.message_input);
		mInputMessageView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int id,
							KeyEvent event) {
						if (id == R.id.send || id == EditorInfo.IME_NULL) {
							attemptSend();
							return true;
						}
						return false;
					}
				});
		mInputMessageView.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (null == mUsername)
					return;
				if (!mSocket.connected())
					return;

				if (!mTyping) {
					mTyping = true;
					mSocket.emit("typing");
				}

				mTypingHandler.removeCallbacks(onTypingTimeout);
				mTypingHandler
						.postDelayed(onTypingTimeout, TYPING_TIMER_LENGTH);
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		ImageButton sendButton = (ImageButton) view
				.findViewById(R.id.send_button);
		sendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				attemptSend();
				Toast.makeText(getActivity(), "SEND", Toast.LENGTH_LONG).show();
			}
		});
	}

//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (Activity.RESULT_OK != resultCode) {
//			getActivity().finish();
//			return;
//		}
//
//		mUsername = data.getStringExtra("username");
//		int numUsers = data.getIntExtra("numUsers", 1);
//	}

	// @Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// inflater.inflate(R.menu.menu_main, menu);
	// }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		return super.onOptionsItemSelected(item);
	}

	private void addLog(String message) {
		mMessages.add(new Message.Builder(Message.TYPE_LOG).message(message)
				.build());
		mAdapter.notifyItemInserted(mMessages.size() - 1);
		scrollToBottom();
	}

	//
	// private void addParticipantsLog(int numUsers) {
	// addLog(getResources().getQuantityString(R.plurals.message_participants,
	// numUsers, numUsers));
	// }

	private void addMessage(String username, String message) {
		mMessages.add(new Message.Builder(Message.TYPE_MESSAGE)
				.username(username).message(message).build());
		mAdapter.notifyItemInserted(mMessages.size() - 1);
		scrollToBottom();
	}

	private void addTyping(String username) {
		mMessages.add(new Message.Builder(Message.TYPE_ACTION).username(
				username).build());
		mAdapter.notifyItemInserted(mMessages.size() - 1);
		scrollToBottom();
	}

	private void removeTyping(String username) {
		for (int i = mMessages.size() - 1; i >= 0; i--) {
			Message message = mMessages.get(i);
			if (message.getType() == Message.TYPE_ACTION
					&& message.getUsername().equals(username)) {
				mMessages.remove(i);
				mAdapter.notifyItemRemoved(i);
			}
		}
	}

	private void attemptSend() {
		if (null == mUsername)
			return;
		if (!mSocket.connected()){
			Toast.makeText(getActivity(), "mất kết nối socket", Toast.LENGTH_LONG).show();
			return;
		}

		mTyping = false;

		String message = mInputMessageView.getText().toString().trim();
		if (TextUtils.isEmpty(message)) {
			mInputMessageView.requestFocus();
			return;
		}

		mInputMessageView.setText("");
		addMessage(mUsername, message);

		// perform the sending message attempt.
		JSONObject data = new JSONObject();
		try {
			data.put("target_type", Global.TARGET_TRIP);
			data.put("target_id", "5623e80833a5eff517534e74");
			data.put("access_token", Global.getPreference(getActivity(), Global.ACCESS_TOKEN, "NONE"));
			data.put("message", message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mSocket.emit("new_message", data);
	}

	private void scrollToBottom() {
		mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
	}

	private Emitter.Listener onConnectError = new Emitter.Listener() {
		@Override
		public void call(Object... args) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getActivity().getApplicationContext(),
							"lỗi kết nối", Toast.LENGTH_LONG).show();
				}
			});
		}
	};

	private Emitter.Listener onNewMessage = new Emitter.Listener() {
		@Override
		public void call(final Object... args) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					JSONObject data = (JSONObject) args[0];
					String username;
					String message;
					try {
						username = data.getJSONObject("sender").getString("fullname");
						
						message = data.getString("message");
					} catch (JSONException e) {
						return;
					}

					removeTyping(username);
					if (username.equalsIgnoreCase(Global.getPreference(getActivity(), Global.USER_FULLNAME, "  "))) {
						return;
					}
					addMessage(username, message);
				}
			});
		}
	};

	private Emitter.Listener onUserJoined = new Emitter.Listener() {
		@Override
		public void call(final Object... args) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					JSONObject data = (JSONObject) args[0];
					String username;
					int numUsers;
					try {
						username = data.getString("username");
						numUsers = data.getInt("numUsers");
					} catch (JSONException e) {
						return;
					}

					// addLog(getResources().getString(
					// R.string.message_user_joined, username));
					// addParticipantsLog(numUsers);
				}
			});
		}
	};

	private Emitter.Listener onUserLeft = new Emitter.Listener() {
		@Override
		public void call(final Object... args) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					JSONObject data = (JSONObject) args[0];
					String username;
					int numUsers;
					try {
						username = data.getString("username");
						numUsers = data.getInt("numUsers");
					} catch (JSONException e) {
						return;
					}

					// addLog(getResources().getString(R.string.message_user_left,
					// username));
					// addParticipantsLog(numUsers);
					// removeTyping(username);
				}
			});
		}
	};

	private Emitter.Listener onTyping = new Emitter.Listener() {
		@Override
		public void call(final Object... args) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					JSONObject data = (JSONObject) args[0];
					String username;
					try {
						username = data.getString("username");
					} catch (JSONException e) {
						return;
					}
					addTyping(username);
				}
			});
		}
	};

	private Emitter.Listener onStopTyping = new Emitter.Listener() {
		@Override
		public void call(final Object... args) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					JSONObject data = (JSONObject) args[0];
					String username;
					try {
						username = data.getString("username");
					} catch (JSONException e) {
						return;
					}
					removeTyping(username);
				}
			});
		}
	};
	private Emitter.Listener onJoin = new Emitter.Listener() {
		@Override
		public void call(final Object... args) {
			JSONObject data1 = (JSONObject)args[0];
			Log.e(".Join", data1.toString());
		}
	};
	private Emitter.Listener ERROR = new Emitter.Listener() {
		@Override
		public void call(final Object... args) {
			JSONObject data = (JSONObject)args[0];
			Log.e(".ERROR", data.toString());
		}
	};

	private Runnable onTypingTimeout = new Runnable() {
		@Override
		public void run() {
			if (!mTyping)
				return;

			mTyping = false;
			mSocket.emit("stop typing");
		}
	};
}
