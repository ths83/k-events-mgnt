import { Action, configureStore, ThunkAction } from "@reduxjs/toolkit";
import eventsSlice from "../../features/eventsSlice";

const store = configureStore({
  reducer: {
    eventsReducer: eventsSlice,
  },
});

export type RootState = ReturnType<typeof store.getState>;

export type AppThunk = ThunkAction<void, RootState, unknown, Action<string>>;

export default store;
