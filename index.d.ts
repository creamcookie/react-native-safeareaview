import * as React from 'react'
import {ViewProps,} from 'react-native'

export interface SAProps extends ViewProps {
    tintLight?: boolean;
}

declare class SafeAreaView extends React.Component<SAProps> {
}
export default SafeAreaView;
