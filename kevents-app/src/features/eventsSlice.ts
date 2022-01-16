import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface Event {
  name: string;
  description: string;
  startDate: string;
  endDate: string;
}

export interface EventsResult {
  events: Event[];
  currentPage: number;
  totalEvents: number;
  totalPages: number;
}

interface Events {
  result: EventsResult;
}

const initialEventsResultState: EventsResult = {
  events: [],
  currentPage: 0,
  totalEvents: 0,
  totalPages: 0,
};

const initialState: Events = {
  result: initialEventsResultState,
};

const eventsSlice = createSlice({
  name: "eventsSlice",
  initialState: initialState,
  reducers: {
    setEvents(state, action: PayloadAction<EventsResult>) {
      state.result = action.payload;
    },
    clearEvents(state) {
      state.result = initialEventsResultState;
    },
  },
});

export const { setEvents, clearEvents } = eventsSlice.actions;
export default eventsSlice.reducer;
