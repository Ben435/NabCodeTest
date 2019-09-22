import React from "react";
import './editable-item-details.scss';
import {withRouter} from "react-router-dom";

class EditableItemDetails extends React.Component {
    constructor(props) {
        super(props);
    }

    updateField(fieldName) {
        return (e) => {
            const newVal = e.target.value;
            const newItem = {
                ...this.props.item,
                [fieldName]: newVal
            };

            this.props.onChange && this.props.onChange(newItem);
        }
    }

    render() {
        const {name, category} = this.props.item;

        return (
            <div className="editable-item-details">
                <label htmlFor='name'><span>Name:</span></label><input id='name' onChange={this.updateField('name').bind(this)}
                                                                       value={name}/>
                <label htmlFor='category'>Category:</label><input id='category'
                                                                  onChange={this.updateField('category').bind(this)}
                                                                  value={category}/>
            </div>
        )
    }
}

export default withRouter(EditableItemDetails);