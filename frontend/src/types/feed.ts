interface FeedType {
  no: number;
  userId: string;
  nickname: string;
  profileImage: string;
  content: string;
  createdAt: Date;
  photoDate: Date;
  weather: string;
  privateMode: boolean;
  images?: string[];
  tags?: string[];
}
interface FeedRequestType {
  userId: string;
  content: string;
  region: string;
  weather: string;
  photoDate: string;
  privateMode: boolean;
  deleteMode: boolean;
  images?: string[];
  tags?: string[];
}

interface StateType {
  feed: FeedType;
  loadFeedLoading: boolean;
  loadFeedDone: boolean;
  loadFeedError: Error | null;
  updateFeedUpdating: boolean;
  updateFeedDone: boolean;
  updateFeedError: Error | null;
  createFeedCreating: boolean;
  createFeedDone: boolean;
  createFeedError: Error | null;
  deleteFeedDeleting: boolean;
  deleteFeedDone: boolean;
  deleteFeedError: Error | null;
}

export type { FeedType, FeedRequestType, StateType };
